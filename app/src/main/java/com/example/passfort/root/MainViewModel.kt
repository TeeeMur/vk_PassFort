package com.example.passfort.root

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel(
    private val preferencesManager: PreferencesManager
) : ViewModel() {
    private val _isUserLoggedIn = MutableStateFlow(preferencesManager.isUserLoggedIn())
    val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn

    fun login() {
        preferencesManager.saveAuthState(true)
        _isUserLoggedIn.value = true
    }

    fun logout() {
        preferencesManager.clearAuthState()
        _isUserLoggedIn.value = false
    }
}

class MainViewModelFactory(
    private val preferencesManager: PreferencesManager
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(preferencesManager) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}