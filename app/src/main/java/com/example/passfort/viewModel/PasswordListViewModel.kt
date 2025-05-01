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

    private var allPasswords = listOf(
        PasswordItem(2, "Гугл рабочий", "passfort@vk.edu", daysToExpire = 5, isCompromised = false),
        PasswordItem(3, "бауманка лкс", "passfort@vk.edu", daysToExpire = 10, isCompromised = true),
        PasswordItem(4, "почта", "arina@mail.ru", daysToExpire = 14, isCompromised = false),
        PasswordItem(5, "Новая заметка 1", "mail123@mail.com", daysToExpire = -1, isCompromised = false),
        PasswordItem(6, "Аэрофлот бонус", "aeroflot@mail.ru", daysToExpire = -1, isCompromised = false),
        PasswordItem(5, "Новая заметка 2", "arina@mail.ru", daysToExpire = -1, isCompromised = false),
        PasswordItem(5, "Новая заметка 2", "arina@mail.ru", daysToExpire = -1, isCompromised = false),
        PasswordItem(5, "Новая заметка 2", "arina@mail.ru", daysToExpire = -1, isCompromised = false),
        PasswordItem(5, "Новая заметка 3", "arina@mail.ru", daysToExpire = -1, isCompromised = false),
        PasswordItem(5, "Новая заметка 5", "arina@mail.ru", daysToExpire = -1, isCompromised = false),
    )

    init {
        viewModelScope.launch { addPasswordRecord() }
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

    suspend fun addPasswordRecord(){
        val listPasswords = repository.getAllPasswords()
        var password = listPasswords[0];
        allPasswords += PasswordItem(
            id = password.id.toInt(),
            name = password.passwordRecordName,
            username = password.passwordRecordLogin,
            daysToExpire = password.passwordLastChangeDate.hour,
            isCompromised = false,
        )
    }
}
