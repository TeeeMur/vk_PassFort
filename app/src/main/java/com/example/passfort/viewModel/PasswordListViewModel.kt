package com.example.passfort.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.passfort.model.PasswordItem
import com.example.passfort.model.dbentity.PasswordRecordEntity
import com.example.passfort.repository.PasswordsListRepo
import com.example.passfort.screen.EScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject


sealed class PasswordListState {
    data object Loading : PasswordListState()
    data class Error(val message: String) : PasswordListState()
    data object Empty : PasswordListState()
    data class Success(val pinnedPasswords: List<PasswordItem>, val allPasswords: List<PasswordItem>) : PasswordListState()
}

data class PasswordsScreenListState(
    val passwordsPinnedList: ImmutableList<PasswordRecordEntity> = persistentListOf(),
    val passwordsNotPinnedList: ImmutableList<PasswordRecordEntity> = persistentListOf(),
    val eScreenState: EScreenState = EScreenState.LOADING
)

@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val repository: PasswordsListRepo
): ViewModel() {

    private val _uiState = MutableStateFlow<PasswordListState>(PasswordListState.Loading)
    val uiState: StateFlow<PasswordListState> = _uiState

    init {
        viewModelScope.launch { loadPasswordRecord() }
    }

    private suspend fun loadPasswordRecord() {
        repository.getAllPasswords().collectLatest()
        { passwordRecords ->
            _uiState.update {
                if (it is PasswordListState.Success) {
                    it.copy(
                        allPasswords = passwordRecords.reversed().map
                        { password ->
                            PasswordItem(
                                id = password.id.toInt(),
                                name = password.passwordRecordName,
                                username = password.passwordRecordLogin,
                                daysToExpire = password.passwordLastChangeDate.hour,
                            )
                        }
                    )
                } else {
                    PasswordListState.Success(
                        allPasswords = passwordRecords.reversed().map
                        { password ->
                            PasswordItem(
                                id = password.id.toInt(),
                                name = password.passwordRecordName,
                                username = password.passwordRecordLogin,
                                daysToExpire = password.passwordLastChangeDate.hour,
                            )
                        },
                        pinnedPasswords = emptyList()
                    )
                }
            }
        }
    }
}
