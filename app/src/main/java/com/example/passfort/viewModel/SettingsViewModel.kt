package com.example.passfort.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class SettingsViewModel: ViewModel() {
    private var _name: MutableStateFlow<String> = MutableStateFlow("")
    private var _surname: MutableStateFlow<String> = MutableStateFlow("")
    private var _login: MutableStateFlow<String> = MutableStateFlow("")
    private var _password: MutableStateFlow<String> = MutableStateFlow("")
    private var _darkThemeEnabled: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var _syncEnabled: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var _notificationsEnabled: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val name: MutableStateFlow<String> = MutableStateFlow("")
    val surname: MutableStateFlow<String> = MutableStateFlow("")
    val login: MutableStateFlow<String> = MutableStateFlow("")
    val password: MutableStateFlow<String> = MutableStateFlow("")
    val darkThemeEnabled: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val syncEnabled: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val notificationsEnabled: MutableStateFlow<Boolean> = MutableStateFlow(false)

    fun updateName(newName: String) {
        name.update { newName }
    }

    fun updateLogin(newLogin: String) {
        login.update { newLogin }
    }

    fun updatePassword(newPassword: String) {
        password.update { newPassword }
    }

    fun updateSurname(newSurname: String) {
        surname.update { newSurname }
    }

}
