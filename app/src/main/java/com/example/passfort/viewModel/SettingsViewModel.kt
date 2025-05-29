package com.example.passfort.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.passfort.designSystem.theme.ChosenTheme
import com.example.passfort.model.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
): ViewModel() {
    private var _name: MutableStateFlow<String> = MutableStateFlow(
        preferencesManager.getName()
    )
    private var _surname: MutableStateFlow<String> = MutableStateFlow(
        preferencesManager.getSurname()
    )
    private var _email: MutableStateFlow<String> = MutableStateFlow(
        preferencesManager.getEmail()
    )
    private var _password: MutableStateFlow<String> = MutableStateFlow(
        preferencesManager.getPassword()
    )
    private var _themeIndex: MutableStateFlow<Int> = MutableStateFlow(
        ChosenTheme.valueOf(preferencesManager.getTheme()).ordinal
    )
    private var _syncEnabled: MutableStateFlow<Boolean> = MutableStateFlow(
        preferencesManager.getSyncEnabled()
    )
    private var _notificationsEnabled: MutableStateFlow<Boolean> = MutableStateFlow(
        preferencesManager.getNotificationsEnabled()
    )
    private var _dataChanged: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val themeNames = persistentListOf(
        "Авто",
        "Светлая",
        "Темная"
    )
    val name: StateFlow<String> = _name.asStateFlow()
    val surname: StateFlow<String> = _surname.asStateFlow()
    val email: StateFlow<String> = _email.asStateFlow()
    val password: StateFlow<String> = _password.asStateFlow()
    val themeIndex: StateFlow<Int> = _themeIndex.asStateFlow()
    val syncEnabled: StateFlow<Boolean> = _syncEnabled.asStateFlow()
    val notificationsEnabled: StateFlow<Boolean> = _notificationsEnabled.asStateFlow()
    val dataChanged: StateFlow<Boolean> = _dataChanged.asStateFlow()

    fun updateName(newName: String) {
        _name.update { newName }
        if (!_dataChanged.value) _dataChanged.update { true }
    }

    fun updateEmail(newLogin: String) {
        _email.update { newLogin }
        if (!_dataChanged.value) _dataChanged.update { true }
    }

    fun updatePassword(newPassword: String) {
        _password.update { newPassword }
        if (!_dataChanged.value) _dataChanged.update { true }
    }

    fun updateSurname(newSurname: String) {
        _surname.update { newSurname }
        if (!_dataChanged.value) _dataChanged.update { true }
    }

    fun changeSyncEnabled() {
        _syncEnabled.update { !_syncEnabled.value }
        preferencesManager.saveSyncEnabled(_syncEnabled.value)
    }

    fun changeThemeIndex(themeIndex: Int) {
        _themeIndex.update { themeIndex }
        preferencesManager.saveTheme(ChosenTheme.entries[_themeIndex.value])
    }

    fun changeNotificationEnabled() {
        _notificationsEnabled.update { !_notificationsEnabled.value }
        preferencesManager.saveNotificationsEnabled(_notificationsEnabled.value)
    }

    fun onSave() {
        preferencesManager.saveName(_name.value)
        preferencesManager.saveSurname(_surname.value)
        preferencesManager.saveEmail(_email.value)
        _dataChanged.update { false }
    }

}
