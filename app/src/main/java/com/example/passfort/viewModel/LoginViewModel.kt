package com.example.passfort.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.passfort.model.PreferencesManager
import com.example.passfort.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val usernameError: String? = null,
    val passwordError: String? = null,
    val loginError: String? = null,
    val isLoading: Boolean = false,
    val loginSuccess: Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    var uiState by mutableStateOf(LoginUiState())
        private set

    fun onUsernameChange(newUsername: String) {
        uiState = uiState.copy(username = newUsername, usernameError = null, loginError = null)
    }

    fun onPasswordChange(newPassword: String) {
        uiState = uiState.copy(password = newPassword, passwordError = null, loginError = null)
    }

    fun onLoginAttempt() {
        if (uiState.isLoading) return

        val validationErrorsState = validateInputs()
        if (validationErrorsState != null) {
            uiState = validationErrorsState
            return
        }

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, loginError = null)
            val result = authRepository.login(
                username = uiState.username,
                password = uiState.password
            )

            result.fold(
                onSuccess = {
                    preferencesManager.setUserLoggedIn(true)
                    uiState = uiState.copy(isLoading = false, loginSuccess = true)
                },
                onFailure = { exception ->
                    val errorMessage = when (exception) {
                        is FirebaseAuthInvalidUserException -> "Пользователь с такой почтой не найден."
                        is FirebaseAuthInvalidCredentialsException -> "Неверный пароль. Попробуйте снова."
                        else -> exception.message ?: "Ошибка входа. Попробуйте снова."
                    }
                    uiState = uiState.copy(
                        isLoading = false,
                        loginError = errorMessage
                    )
                }
            )
        }
    }

    private fun validateInputs(): LoginUiState? {
        val usernameError = when {
            uiState.username.isBlank() -> "Введите почту"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(uiState.username).matches() -> "Неверный формат почты"
            else -> null
        }
        val passwordError = if (uiState.password.isBlank()) "Введите пароль" else null

        return if (usernameError == null && passwordError == null) {
            null
        } else {
            uiState.copy(
                usernameError = usernameError,
                passwordError = passwordError,
                isLoading = false
            )
        }
    }

    fun resetState() {
        uiState = LoginUiState()
    }

    fun consumeLoginSuccessEvent() {
        uiState = uiState.copy(loginSuccess = false)
    }
}