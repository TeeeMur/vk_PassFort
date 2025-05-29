package com.example.passfort.model

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.example.passfort.designSystem.theme.ChosenTheme

class PreferencesManager(context: Context) {
    val prefs: SharedPreferences = context.getSharedPreferences("passfort_prefs", Context.MODE_PRIVATE)

    fun saveTheme(chosenTheme: ChosenTheme) {
        prefs.edit(commit = true) {
            putString("app_theme", chosenTheme.name)
        }
    }
    fun getTheme(): String = prefs.getString("app_theme", ChosenTheme.AUTO.name).toString()

    fun clearAuthState() {
        prefs.edit { clear() }
    }

    fun saveSyncEnabled(enabled: Boolean) {
        prefs.edit(commit = true) {
            putBoolean("sync_enabled", enabled)
        }
    }
    fun getSyncEnabled(): Boolean = prefs.getBoolean("sync_enabled", false)

    fun saveName(userName: String) {
        prefs.edit(commit = true) {
            putString("user_name", userName)
        }
    }
    fun getName(): String = prefs.getString("user_name", "").toString()

    fun saveSurname(surname: String) {
        prefs.edit(commit = true) {
            putString("user_surname", surname)
        }
    }
    fun getSurname(): String = prefs.getString("user_surname", "").toString()

    fun savePassword(password: String) {
        prefs.edit(commit = true) {
            putString("user_password", password)
        }
    }
    fun getPassword(): String = prefs.getString("user_password", "").toString()

    fun saveAuthState(isLoggedIn: Boolean) {
        prefs.edit {
            putBoolean("is_user_logged_in", isLoggedIn)
        }
    }
    fun isUserLoggedIn(): Boolean = prefs.getBoolean("is_user_logged_in", false)

    fun savePin(pin: String) {
        prefs.edit {
            putString("user_pin", pin)
        }
    }
    fun getPin(): String? = prefs.getString("user_pin", null)
    fun hasPin(): Boolean = getPin() != null

    fun saveNotificationsEnabled(enabled: Boolean) {
        prefs.edit(commit = true) {
            putBoolean("notifications_enabled", enabled)
        }
    }
    fun getNotificationsEnabled(): Boolean = prefs.getBoolean("notifications_enabled", false)
    fun saveEmail(email: String) {
        prefs.edit(commit = true) {
            putString("user_email", email)
        }
    }
    fun getEmail(): String = prefs.getString("user_email", "").toString()
}