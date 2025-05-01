package com.example.passfort.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passfort.model.dbentity.PasswordRecordEntity
import com.example.passfort.repository.PasswordsListRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val repository: PasswordsListRepo
) : ViewModel() {

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
        viewModelScope.launch { createPassword() }
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
    
    suspend fun createPassword() {
        repository.upsertPassword(
            password = PasswordRecordEntity(
                passwordRecordName = "123",
                passwordRecordLogin = "LOgin",
                passwordRecordPassword = "345432",
                passwordLastChangeDate = LocalDateTime.now(),
                passwordChangeIntervalDays = 0,
                iconIndex = 0,
                pinned = false,
                passwordLastUsedDate = LocalDateTime.now()
            )
        )
    }
}