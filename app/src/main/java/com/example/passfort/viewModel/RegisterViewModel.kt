


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val isLoading: Boolean = false
)

class RegisterViewModel : ViewModel() {

    var uiState by mutableStateOf(RegisterUiState())
        private set

    private val _eventFlow = MutableSharedFlow<RegisterEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onNameChange(newName: String) {
        uiState = uiState.copy(name = newName, nameError = null)
    }

    fun onEmailChange(newEmail: String) {
        uiState = uiState.copy(email = newEmail, emailError = null)
    }

    fun onPasswordChange(newPassword: String) {
        uiState = uiState.copy(password = newPassword, passwordError = null)
    }

    fun onConfirmPasswordChange(newConfirmPassword: String) {
        uiState = uiState.copy(confirmPassword = newConfirmPassword, confirmPasswordError = null)
    }

    fun onRegisterAttempt() {
        if (uiState.isLoading) return

        viewModelScope.launch {
            uiState = uiState.copy(
                isLoading = true,
                nameError = null,
                emailError = null,
                passwordError = null,
                confirmPasswordError = null
            )

            kotlinx.coroutines.delay(1000)

            val errors = validateInputs()

            if (errors == null) {
                uiState = uiState.copy(isLoading = false)
                _eventFlow.emit(RegisterEvent.Success)
            } else {
                uiState = errors.copy(isLoading = false)
            }
        }
    }

    private fun validateInputs(): RegisterUiState? {
        val nameError = if (uiState.name.isBlank()) "Введите имя" else null
        val emailError = if (uiState.email.isBlank()) "Введите почту" else null
        val passwordError = if (uiState.password.isBlank()) "Введите пароль" else null
        val confirmPasswordError = when {
            uiState.confirmPassword.isBlank() -> "Повторите пароль"
            uiState.confirmPassword != uiState.password -> "Пароли не совпадают"
            else -> null
        }

        return if (listOf(nameError, emailError, passwordError, confirmPasswordError).all { it == null }) {
            null
        } else {
            uiState.copy(
                nameError = nameError,
                emailError = emailError,
                passwordError = passwordError,
                confirmPasswordError = confirmPasswordError
            )
        }
    }

    fun resetState() {
        uiState = RegisterUiState()
    }
}

sealed class RegisterEvent {
    object Success : RegisterEvent()
}