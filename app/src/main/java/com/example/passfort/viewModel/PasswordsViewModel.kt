package com.example.passfort.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passfort.dbentity.PasswordRecordEntity
import com.example.passfort.repository.PasswordsRepoImpl
import com.example.passfort.repository.ScreenListState
import com.example.passfort.repository.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordsViewModel @Inject constructor(private val repo: PasswordsRepoImpl): ViewModel() {

    private val _passwordsStateFlow: MutableStateFlow<ScreenListState> = MutableStateFlow(ScreenListState())
    val passwords = _passwordsStateFlow.asStateFlow()

    fun refreshPasswords() {
        viewModelScope.launch {
            _passwordsStateFlow.value.screenState = ScreenState.Loading
            _passwordsStateFlow.update { value ->
                try {
                    val resPinnedList = repo.getPinnedPasswords()
                    val resNotPinnedList = repo.getNonPinnedPasswords()
                    ScreenListState(resPinnedList, resNotPinnedList, ScreenState.Success)
                } catch(_: Exception) {
                    ScreenListState(value.passwordsNotPinnedList, value.passwordsPinnedList, ScreenState.Error)
                }
            }
        }
    }

    fun pinPassword(passwordRecordEntity: PasswordRecordEntity) {
        viewModelScope.launch {
            passwordRecordEntity.pinned = true
            repo.upsertPassword(passwordRecordEntity)
            this@PasswordsViewModel.refreshPasswords()
        }
    }

    fun setNewPassword(passwordRecordEntity: PasswordRecordEntity, password: String) {
        viewModelScope.launch {
            passwordRecordEntity.passwordRecordPassword = password
            repo.upsertPassword(passwordRecordEntity)
            this@PasswordsViewModel.refreshPasswords()
        }
    }
}