package com.example.passfort.viewModel

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import com.example.passfort.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import java.security.SecureRandom

@HiltViewModel
class CreateViewModel @Inject constructor() : ViewModel() {

    private val _namePassword: MutableStateFlow<String> = MutableStateFlow("")
    private val _login: MutableStateFlow<String> = MutableStateFlow("")
    private val _password: MutableStateFlow<String> = MutableStateFlow("")
    private val _note: MutableStateFlow<String> = MutableStateFlow("")

    val namePassword: StateFlow<String> = _namePassword.asStateFlow()
    val login: StateFlow<String> = _login.asStateFlow()
    val password: StateFlow<String> = _password.asStateFlow()
    val note: StateFlow<String> = _note.asStateFlow()

    private val _enablePasswordChange: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val enablePasswordChange: StateFlow<Boolean> = _enablePasswordChange.asStateFlow()

    init {
        _namePassword.update { "Новый пароль" }
    }

    fun onNamePasswordChange(newNamePassword: String) {
        _namePassword.update { newNamePassword }
    }

    fun onLoginChange(newUsername: String) {
        _login.update { newUsername }
    }

    fun onPasswordChange(newPassword: String) {
        _password.update { newPassword }
    }

    fun onNoteChange(newNote: String) {
        _note.update { newNote }
    }

    fun setPasswordChange() {
        _enablePasswordChange.update { !it }
    }

    fun setPasswordDaysCount(daysCount: Int) {

    }
}