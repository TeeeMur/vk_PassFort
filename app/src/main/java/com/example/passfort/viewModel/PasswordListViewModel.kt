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
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime
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
                                id = password.id,
                                iconId = 0,
                                itemName = password.recordName,
                                itemLogin = password.recordLogin,
                                itemPassword = password.recordPassword,
                                isPinned = password.pinned,
                                imageCardUri = password.imageCardUri
                            )
                        },
                        pinnedPasswords = passwordRecords.reversed().filter { it -> it.pinned }.map
                        { password ->
                            PasswordItem(
                                id = password.id,
                                iconId = 0,
                                itemName = password.recordName,
                                itemLogin = password.recordLogin,
                                itemPassword = password.recordPassword,
                                isPinned = password.pinned,
                                imageCardUri = password.imageCardUri
                            )
                        }
                    )
                } else {
                    PasswordListState.Success(
                        allPasswords = passwordRecords.reversed().map
                        { password ->
                            PasswordItem(
                                id = password.id,
                                iconId = 0,
                                itemName = password.recordName,
                                itemLogin = password.recordLogin,
                                itemPassword = password.recordPassword,
                                isPinned = password.pinned,
                                imageCardUri = password.imageCardUri
                            )
                        },
                        pinnedPasswords = passwordRecords.reversed().filter { it -> it.pinned }.map
                        { password ->
                            PasswordItem(
                                id = password.id,
                                iconId = 0,
                                itemName = password.recordName,
                                itemLogin = password.recordLogin,
                                itemPassword = password.recordPassword,
                                isPinned = password.pinned,
                                imageCardUri = password.imageCardUri
                            )
                        }
                    )
                }
            }
        }
    }

    fun pinPassword(passwordId: Long) {
        viewModelScope.launch {
            var password = repository.getByIdPassword(passwordId)
            password.pinned = !password.pinned
            repository.pinPassword(password)
        }
    }

    fun deletePassword(passwordId: Long) {
        viewModelScope.launch {
            repository.deletePassword(passwordId)
        }
    }
}
