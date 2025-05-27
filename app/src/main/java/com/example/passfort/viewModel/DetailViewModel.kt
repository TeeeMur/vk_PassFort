package com.example.passfort.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passfort.model.dbentity.PasswordRecordEntity
import com.example.passfort.repository.PasswordsDetailRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: PasswordsDetailRepo
) : ViewModel() {

    private val _idPassword: MutableStateFlow<Long> = MutableStateFlow(0)
    private val _namePassword: MutableStateFlow<String> = MutableStateFlow("")
    private val _login: MutableStateFlow<String> = MutableStateFlow("")
    private val _password: MutableStateFlow<String> = MutableStateFlow("")
    private val _note: MutableStateFlow<String> = MutableStateFlow("")
    private val _changeIntervalDays: MutableStateFlow<Int> = MutableStateFlow(0)
    private val _changeIntervalDaysIndex: MutableStateFlow<Int> = MutableStateFlow(0)
    private val _isPinned: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val _isEmptyRecords: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _isChangedRecords: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val _enablePasswordChange: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val namePassword: StateFlow<String> = _namePassword.asStateFlow()
    val login: StateFlow<String> = _login.asStateFlow()
    val password: StateFlow<String> = _password.asStateFlow()
    val note: StateFlow<String> = _note.asStateFlow()
    val changeIntervalDays: StateFlow<Int> = _changeIntervalDays.asStateFlow()
    val changeIntervalDaysIndex: StateFlow<Int> = _changeIntervalDaysIndex.asStateFlow()
    val isPinned: StateFlow<Boolean> = _isPinned.asStateFlow()

    val isChangedRecords: StateFlow<Boolean> = _isEmptyRecords.asStateFlow()
    val isEmptyRecords: StateFlow<Boolean> = _isEmptyRecords.asStateFlow()
    val enablePasswordChange: StateFlow<Boolean> = _enablePasswordChange.asStateFlow()

    fun initPassword(
        idPassword: Long
    ){
        viewModelScope.launch {
            val password = repository.getPassword(idPassword)

            _idPassword.update { password.id }
            _namePassword.update { password.recordName }
            _login.update { password.recordLogin }
            _password.update { password.recordPassword }
            _note.update { password.recordNote }
            _changeIntervalDays.update { password.passwordChangeIntervalDays }
            _changeIntervalDaysIndex.update{
                PASS_CHANGE_NOTIFICATION_INTERVAL_OPTIONS.indexOf(_changeIntervalDays.value.toString())
            }
            _isPinned.update { password.pinned }

            _enablePasswordChange.update { _changeIntervalDays.value > 0 }
        }
    }

    fun onNamePasswordChange(newNamePassword: String) {
        _namePassword.update { newNamePassword }
        onChanged()
    }

    fun onLoginChange(newUsername: String) {
        _login.update { newUsername }
        onChanged()
    }

    fun onPasswordChange(newPassword: String) {
        _password.update { newPassword }
        onChanged()
    }

    fun onNoteChange(newNote: String) {
        _note.update { newNote }
        onChanged()
    }

    fun setChangeIntervalDaysCountIndex(daysCountIndex: Int) {
        _changeIntervalDaysIndex.update { daysCountIndex }
        _changeIntervalDays.update { PASS_CHANGE_NOTIFICATION_INTERVAL_OPTIONS[daysCountIndex].toInt() }
        onChanged()
    }

    fun setPinnedState() {
        _isPinned.update { !_isPinned.value }
        onChanged()
    }

    fun setPasswordChange() {
        _enablePasswordChange.update { !it }
        if (!_enablePasswordChange.value) {
            _changeIntervalDays.update { 0 }
        } else {
            _changeIntervalDaysIndex.update { 0 }
        }
    }

    fun deletePassword() {
        viewModelScope.launch {
            repository.deletePassword(
                password = PasswordRecordEntity(
                    id = _idPassword.value,
                    recordName = _namePassword.value,
                    recordLogin = _login.value,
                    recordPassword = _password.value,
                    recordNote = _note.value,
                    passwordLastChangeDate = LocalDateTime.now(),
                    passwordChangeIntervalDays = _changeIntervalDays.value,
                    iconIndex = 0,
                    pinned = _isPinned.value,
                    passwordLastUsedDate = LocalDateTime.now()
                )
            )
        }
    }

    fun editPassword(): Boolean {

        if (isEmptyRecords()){
            _isEmptyRecords.update { true }
            return false
        }

        viewModelScope.launch {
            repository.upsertPassword(
                password = PasswordRecordEntity(
                    id = _idPassword.value,
                    recordName = _namePassword.value,
                    recordLogin = _login.value,
                    recordPassword = _password.value,
                    recordNote = _note.value,
                    passwordLastChangeDate = LocalDateTime.now(),
                    passwordChangeIntervalDays = _changeIntervalDays.value,
                    iconIndex = 0,
                    pinned = _isPinned.value,
                    passwordLastUsedDate = LocalDateTime.now()
                )
            )
        }
        _isEmptyRecords.update { false }
        return true
    }

    private fun isEmptyRecords(): Boolean{
        return _login.value == "" || _password.value == ""
    }

    private fun onChanged(){
        if (!_isChangedRecords.value){
            _isChangedRecords.update { true }
        }
    }
}

