package com.example.passfort.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passfort.model.PasswordItem
import com.example.passfort.repository.PasswordsListRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class PasswordListState {
    data object Loading : PasswordListState()
    data class Error(val message: String) : PasswordListState()
    data object Empty : PasswordListState()
    data class Success(
        val pinnedPasswords: List<PasswordItem>,
        val allPasswords: List<PasswordItem>,
        val searchPasswords: List<PasswordItem>,
    ) : PasswordListState()
}

@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val repository: PasswordsListRepo
) : ViewModel() {

    private val _uiState = MutableStateFlow<PasswordListState>(PasswordListState.Loading)
    val uiState: StateFlow<PasswordListState> = _uiState

    private val _searchPattern = MutableStateFlow<String>("")
    val searchPattern = _searchPattern.asStateFlow()

    fun search(pattern: String) {
        _searchPattern.update { pattern }
    }

    init {
        viewModelScope.launch { loadPasswordRecord() }
        _searchPattern.onEach { loadSearchPasswords() }.launchIn(viewModelScope)
    }

    private suspend fun loadSearchPasswords() {
        val searchResult = repository.getPasswordsByName(_searchPattern.value).map{it.convertToPasswordItem()}
        _uiState.update { oldIt ->
            if (oldIt is PasswordListState.Success) {
                oldIt.copy(searchPasswords = searchResult)
            } else {
                PasswordListState.Success(
                    allPasswords = emptyList(),
                    pinnedPasswords = emptyList(),
                    searchPasswords = searchResult
                )
            }
        }
    }

    private suspend fun loadPasswordRecord() {
        repository.getAllPasswords().collectLatest()
        { passwordRecords ->
            _uiState.update { oldIt ->
                if (oldIt is PasswordListState.Success) {
                    oldIt.copy(
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
                        },
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
                        },
                        searchPasswords = emptyList()
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
