package com.example.passfort.viewModel

import android.accounts.NetworkErrorException
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.provider.Settings.Global.getString
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
import java.io.IOException
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject
import com.example.passfort.R
import com.example.passfort.repository.NetworkChecker
import dagger.hilt.android.qualifiers.ApplicationContext
import java.net.SocketTimeoutException

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val usernameError: String? = null,
    val passwordError: String? = null,
    val loginError: String? = null,
    val isLoading: Boolean = false,
    val loginSuccess: Boolean = false
)

class NetworkErrorException(message: String, cause: Throwable? = null) : IOException(message, cause)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val preferencesManager: PreferencesManager,
    private val networkChecker: NetworkChecker,
    @ApplicationContext private val context: Context
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
        if (!networkChecker.isNetworkAvailable()) {
            uiState = uiState.copy(
                isLoading = false,
                loginError = "Отсутствует подключение к интернету. Пожалуйста, проверьте ваше соединение."
            )
            return
        }

        val validationErrorsState = validateInputs()
        if (validationErrorsState.usernameError != null || validationErrorsState.passwordError != null) {
            uiState = validationErrorsState
            return
        }

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, loginError = null)
            try {
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
                            is com.example.passfort.viewModel.NetworkErrorException -> "Сетевая ошибка. Пожалуйста, проверьте ваше соединение и попробуйте снова."
                            is FirebaseAuthInvalidCredentialsException -> "Неверный пароль или логин. Попробуйте снова."
                            is NetworkErrorException -> "Сетевая ошибка. Пожалуйста, проверьте ваше соединение и попробуйте снова."
                            is UnknownHostException -> "Не удалось подключиться к серверу. Проверьте ваше интернет-соединение."
                            is SocketTimeoutException -> "Время ожидания ответа от сервера истекло. Попробуйте позже."
                            is ConnectException -> "Не удалось установить соединение с сервером. Попробуйте позже."
                            is IOException -> "Ошибка сети. Пожалуйста, проверьте ваше соединение и попробуйте снова."
                            else -> exception.localizedMessage ?: "Ошибка входа. Попробуйте снова."
                        }
                        uiState = uiState.copy(
                            isLoading = false,
                            loginError = errorMessage
                        )
                    }
                )
            } catch (e: Exception) {
                val fallbackErrorMessage = when (e) {
                    is UnknownHostException -> "Не удалось подключиться к серверу. Проверьте ваше интернет-соединение."
                    is SocketTimeoutException -> "Время ожидания ответа от сервера истекло. Попробуйте позже."
                    is ConnectException -> "Не удалось установить соединение с сервером. Попробуйте позже."
                    is com.example.passfort.viewModel.NetworkErrorException -> "Сетевая ошибка. Пожалуйста, проверьте ваше соединение и попробуйте снова."
                    is IOException -> "Ошибка сети. Пожалуйста, проверьте ваше соединение и попробуйте снова."
                    else -> e.localizedMessage ?: "Произошла непредвиденная ошибка. Попробуйте снова."
                }
                uiState = uiState.copy(
                    isLoading = false,
                    loginError = fallbackErrorMessage
                )
            }
        }
    }

    private fun validateInputs(): LoginUiState {
        val usernameError = when {
            uiState.username.isBlank() -> "Введите почту"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(uiState.username).matches() -> "Неверный формат почты"
            else -> null
        }
        val passwordError = if (uiState.password.isBlank()) "Введите пароль" else null

        return uiState.copy(
            usernameError = usernameError,
            passwordError = passwordError,
            isLoading = false
        )
    }

    fun resetState() {
        uiState = LoginUiState()
    }

    fun consumeLoginSuccessEvent() {
        uiState = uiState.copy(loginSuccess = false)
    }

    fun errorDialogDismissed() {
        uiState = uiState.copy(
            usernameError = null,
            passwordError = null,
            loginError = null
        )
    }


}