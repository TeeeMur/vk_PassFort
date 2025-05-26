package com.example.passfort.model

import android.content.Context
import androidx.core.content.edit

class PreferencesManager(context: Context) {
    private val prefs = context.getSharedPreferences("passfort_prefs", Context.MODE_PRIVATE)

    fun saveAuthState(isLoggedIn: Boolean) {
        prefs.edit {
            putBoolean("is_user_logged_in", isLoggedIn)
        }
    }

    fun clearAuthState() {
        prefs.edit { clear() }
    }

    fun isUserLoggedIn(): Boolean = prefs.getBoolean("is_user_logged_in", false)

    fun savePin(pin: String) {
        prefs.edit {
            putString("user_pin", pin)
        }
    }

    fun getPin(): String? = prefs.getString("user_pin", null)

    fun hasPin(): Boolean = getPin() != null
}