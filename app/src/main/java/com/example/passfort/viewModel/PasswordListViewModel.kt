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
import javax.inject.Inject


sealed class PasswordListState {
    object Loading : PasswordListState()
    data class Error(val message: String) : PasswordListState()
    object Empty : PasswordListState()
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

    private val pinnedPasswords = listOf(
        PasswordItem(1, "почта", "arina@mail.ru", daysToExpire = -1, isCompromised = false)
    )

    private var allPasswords = emptyList<PasswordItem>()

    init {
        viewModelScope.launch { loadPasswordRecord() }
        loadPasswords()
    }

    private fun loadPasswords() {
        viewModelScope.launch {
            _uiState.value = PasswordListState.Loading
            delay(1500)  // имитируем задержку загрузки данных
            _uiState.value = PasswordListState.Empty
            if (pinnedPasswords.isEmpty() && allPasswords.isEmpty()) {
                _uiState.value = PasswordListState.Empty
            } else {
                _uiState.value = PasswordListState.Success(pinnedPasswords, allPasswords)
                //_uiState.value = PasswordListState.Error("Ошибка загрузки данных. Проверьте соединение.")
            }
        }
    }

    fun retry() {
        loadPasswords()
    }

    suspend fun loadPasswordRecord(){
        val listPasswords = repository.getAllPasswords()
        listPasswords.forEach {
            allPasswords += PasswordItem(
                id = it.id.toInt(),
                name = it.passwordRecordName,
                username = it.passwordRecordLogin,
                daysToExpire = it.passwordLastChangeDate.hour,
                isCompromised = false
            )
        }
    }
}
