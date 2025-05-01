package com.example.passfort.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passfort.model.dbentity.PasswordRecordEntity
import com.example.passfort.repository.PasswordsListRepo
import com.example.passfort.screen.EScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordsViewModel @Inject constructor(private val repo: PasswordsListRepo): ViewModel() {

    private val _passwordsStateFlow: MutableStateFlow<PasswordsScreenListState> = MutableStateFlow(PasswordsScreenListState())
    val passwords = _passwordsStateFlow.asStateFlow()

    fun refreshPasswords() {
        viewModelScope.launch {
            _passwordsStateFlow.update { value ->
                value.copy(eScreenState = EScreenState.LOADING)
            }
            _passwordsStateFlow.update { value ->
                try {
                    val resPinnedList = repo.getPinnedPasswords().toImmutableList()
                    val resNotPinnedList = repo.getNonPinnedPasswords().toImmutableList()
                    PasswordsScreenListState(resPinnedList, resNotPinnedList, EScreenState.SUCCESS)
                } catch(_: Exception) {
                    value.copy(eScreenState = EScreenState.ERROR)
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