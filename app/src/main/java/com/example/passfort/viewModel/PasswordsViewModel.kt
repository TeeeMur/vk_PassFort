package com.example.passfort.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passfort.dbentity.PasswordRecordEntity
import com.example.passfort.repository.PasswordsRepo
import com.example.passfort.ui.PasswordsScreenListState
import com.example.passfort.ui.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordsViewModel @Inject constructor(private val repo: PasswordsRepo): ViewModel() {

    private val _passwordsStateFlow: MutableStateFlow<PasswordsScreenListState> = MutableStateFlow(PasswordsScreenListState())
    val passwords = _passwordsStateFlow.asStateFlow()

    fun refreshPasswords() {
        viewModelScope.launch {
            _passwordsStateFlow.update { value ->
                value.copy(screenState = ScreenState.LOADING)
            }
            _passwordsStateFlow.update { value ->
                try {
                    val resPinnedList = repo.getPinnedPasswords()
                    val resNotPinnedList = repo.getNonPinnedPasswords()
                    PasswordsScreenListState(resPinnedList, resNotPinnedList, ScreenState.SUCCESS)
                } catch(_: Exception) {
                    value.copy(screenState = ScreenState.ERROR)
                }
            }
        }
    }

    fun upsertPassword(passwordRecordEntity: PasswordRecordEntity) {
        viewModelScope.launch {
            repo.upsertPassword(passwordRecordEntity)
            this@PasswordsViewModel.refreshPasswords()
        }
    }

    fun deletePassword(passwordRecordEntity: PasswordRecordEntity) {
        viewModelScope.launch {
            repo.deletePassword(passwordRecordEntity)
            this@PasswordsViewModel.refreshPasswords()
        }
    }
}