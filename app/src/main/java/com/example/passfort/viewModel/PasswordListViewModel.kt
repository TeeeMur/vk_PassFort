package com.example.passfort.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.passfort.model.PasswordItem
import com.example.passfort.model.dbentity.PasswordRecordEntity
import com.example.passfort.screen.EScreenState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf


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

class PasswordViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<PasswordListState>(PasswordListState.Loading)
    val uiState: StateFlow<PasswordListState> = _uiState


    init {
        loadPasswords()
    }

    private fun loadPasswords() {
        viewModelScope.launch {
            _uiState.value = PasswordListState.Loading
        }
    }

    fun retry() {
        loadPasswords()
    }
}
