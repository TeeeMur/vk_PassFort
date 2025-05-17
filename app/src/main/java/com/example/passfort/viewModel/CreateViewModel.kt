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
    private val _changeIntervalDays: MutableStateFlow<Int> = MutableStateFlow(0)
    private val _isEmptyRecords: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val namePassword: StateFlow<String> = _namePassword.asStateFlow()
    val login: StateFlow<String> = _login.asStateFlow()
    val password: StateFlow<String> = _password.asStateFlow()
    val note: StateFlow<String> = _note.asStateFlow()
    val changeIntervalDays: StateFlow<Int> = _changeIntervalDays.asStateFlow()
    val isEmptyRecords: StateFlow<Boolean> = _isEmptyRecords.asStateFlow()

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

    fun setChangeIntervalDaysCount(daysCount: Int) {
        _changeIntervalDays.update { daysCount }
    }
    
    fun createPassword(): Boolean {

        if (_login.value == "" || _password.value == ""){
            _isEmptyRecords.update { true }
            return false
        }

        viewModelScope.launch {
            repository.upsertPassword(
                password = PasswordRecordEntity(
                    passwordRecordName = _namePassword.value,
                    passwordRecordLogin = _login.value,
                    passwordRecordPassword = _password.value,
                    passwordLastChangeDate = LocalDateTime.now(),
                    passwordChangeIntervalDays = _changeIntervalDays.value,
                    iconIndex = 0,
                    pinned = false,
                    passwordLastUsedDate = LocalDateTime.now()
                )
            )
        }
        reset()
        return true
    }

    private fun reset(){
        _namePassword.update { "Новый пароль" }
        _login.update { "" }
        _password.update { "" }
        _note.update { "" }
        _changeIntervalDays.update { 0 }
        _isEmptyRecords.update { false }
    }
}