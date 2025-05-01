package com.example.passfort.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.passfort.model.PreferencesManager
import kotlinx.coroutines.launch


data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val usernameError: String? = null,
    val passwordError: String? = null,
    val loginSuccess: Boolean = false
)

class LoginViewModel (
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    var uiState by mutableStateOf(LoginUiState())
        private set

    fun onUsernameChange(newUsername: String) {
        uiState = uiState.copy(username = newUsername, usernameError = null, loginSuccess = false)
    }

    fun onPasswordChange(newPassword: String) {
        uiState = uiState.copy(password = newPassword, passwordError = null, loginSuccess = false)
    }

    fun onLoginAttempt() {
        if (uiState.isLoading || uiState.loginSuccess) return

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, usernameError = null,passwordError = null, loginSuccess = false)
            kotlinx.coroutines.delay(1000)

            if (uiState.username.isNotBlank() && uiState.password.isNotBlank()) {
                preferencesManager.saveAuthState(true)
                uiState = uiState.copy(isLoading = false, loginSuccess = true)
            } else {
                uiState = uiState.copy(
                    isLoading = false,
                    passwordError = "Введите пароль",
                    usernameError = "Введите логин",
                    loginSuccess = false
                )
            }
        }
    }

    fun consumeLoginSuccessEvent() {
        if (uiState.loginSuccess) {
            uiState = uiState.copy(loginSuccess = false)
        }
    }

    fun resetState() {
        uiState = LoginUiState()
    }

    // --- Фабрика (ВРЕМЕННО, пока Hilt не работает) ---
    companion object {
        fun provideFactory(
            preferencesManager: PreferencesManager
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                    return LoginViewModel(preferencesManager) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}