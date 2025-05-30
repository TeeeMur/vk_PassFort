package com.example.passfort.ui.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passfort.repository.NetworkChecker
import com.example.passfort.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val registrationError: String? = null,
    val isLoading: Boolean = false,
    val generalError: String? = null
)

sealed class RegisterEvent {
    object Success : RegisterEvent()
}

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val networkChecker: NetworkChecker
) : ViewModel() {

    var uiState by mutableStateOf(RegisterUiState())
        private set

    private val _eventFlow = MutableSharedFlow<RegisterEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onNameChange(newName: String) {
        uiState = uiState.copy(name = newName, nameError = null, registrationError = null)
    }

    fun onEmailChange(newEmail: String) {
        uiState = uiState.copy(email = newEmail, emailError = null, registrationError = null)
    }

    fun onPasswordChange(newPassword: String) {
        uiState = uiState.copy(password = newPassword, passwordError = null, registrationError = null)
    }

    fun onConfirmPasswordChange(newConfirmPassword: String) {
        uiState = uiState.copy(confirmPassword = newConfirmPassword, confirmPasswordError = null, registrationError = null)
    }

    fun onRegisterAttempt() {
        if (uiState.isLoading) return

        if (!networkChecker.isNetworkAvailable()) {
            uiState = uiState.copy(
                generalError = "Отсутствует подключение к интернету. Пожалуйста, проверьте ваше соединение."
            )
            return
        }

        val validationError = validateInputs()
        if (validationError != null) {
            uiState = uiState.copy(generalError = validationError)
            return
        }

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            val result = authRepository.register(
                name = uiState.name,
                email = uiState.email,
                password = uiState.password
            )

            result.fold(
                onSuccess = {
                    uiState = uiState.copy(isLoading = false)
                    _eventFlow.emit(RegisterEvent.Success)
                },
                onFailure = { exception ->
                    val errorMessage = when (exception) {
                        is FirebaseAuthUserCollisionException -> "Этот email уже используется."
                        is FirebaseAuthWeakPasswordException -> "Пароль слишком слабый. Используйте не менее 6 символов."
                        else -> exception.message ?: "Ошибка регистрации. Попробуйте снова."
                    }
                    uiState = uiState.copy(isLoading = false, generalError = errorMessage)
                }
            )
        }
    }

    private fun validateInputs(): String? {
        return when {
            uiState.name.isBlank() -> "Введите имя"
            uiState.email.isBlank() -> "Введите почту"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(uiState.email).matches() -> "Неверный формат почты"
            uiState.password.isBlank() -> "Введите пароль"
            uiState.password.length < 6 -> "Пароль должен быть не менее 6 символов"
            uiState.confirmPassword.isBlank() -> "Повторите пароль"
            uiState.confirmPassword != uiState.password -> "Пароли не совпадают"
            else -> null
        }
    }
    fun dismissErrorDialog() {
        uiState = uiState.copy(generalError = null)
    }

    fun resetState() {
        uiState = RegisterUiState()
    }
}