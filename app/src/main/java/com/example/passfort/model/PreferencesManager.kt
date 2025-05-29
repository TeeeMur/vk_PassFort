package com.example.passfort.model

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.passfort.designSystem.theme.ChosenTheme
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesManager @Inject constructor(@ApplicationContext context: Context) {

    val prefs: SharedPreferences =
        context.getSharedPreferences("passfort_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_IS_USER_LOGGED_IN = "is_user_logged_in"
        private const val KEY_USER_PIN = "user_pin"
        private const val KEY_APP_THEME = "app_theme"
        private const val KEY_SYNC_ENABLED = "sync_enabled"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_USER_SURNAME = "user_surname"
        private const val KEY_USER_PASSWORD = "user_password"
        private const val KEY_NOTIFICATIONS_ENABLED = "notifications_enabled"
        private const val KEY_USER_EMAIL = "email" // Consistent key for email
    }

    // --- User Authentication and State ---
    fun setUserLoggedIn(isLoggedIn: Boolean) {
        prefs.edit {
            putBoolean(KEY_IS_USER_LOGGED_IN, isLoggedIn)
        }
    }

    fun isUserLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_USER_LOGGED_IN, false)
    }

    fun clearLoginState() {
        prefs.edit {
            remove(KEY_IS_USER_LOGGED_IN)
        }
    }

    fun clearAllPreferences() {
        prefs.edit { clear() }
    }

    // --- Theme Management ---
    fun saveTheme(chosenTheme: ChosenTheme) {
        prefs.edit(commit = true) { // commit=true is usually not necessary with .apply()
            putString(KEY_APP_THEME, chosenTheme.name)
        }
    }

    fun getTheme(): String = prefs.getString(KEY_APP_THEME, ChosenTheme.AUTO.name).toString()

    // --- Sync Settings ---
    fun saveSyncEnabled(enabled: Boolean) {
        prefs.edit { // No need for commit=true or nested edit blocks
            putBoolean(KEY_SYNC_ENABLED, enabled)
        }
    }

    fun getSyncEnabled(): Boolean = prefs.getBoolean(KEY_SYNC_ENABLED, false)

    // --- User Profile Information ---
    fun saveName(userName: String) {
        prefs.edit {
            putString(KEY_USER_NAME, userName)
        }
    }

    fun getName(): String = prefs.getString(KEY_USER_NAME, "").toString()

    fun saveSurname(surname: String) {
        prefs.edit {
            putString(KEY_USER_SURNAME, surname)
        }
    }

    fun getSurname(): String = prefs.getString(KEY_USER_SURNAME, "").toString()

    fun saveEmail(email: String) {
        prefs.edit {
            putString(KEY_USER_EMAIL, email)
        }
    }

    fun getEmail(): String = prefs.getString(KEY_USER_EMAIL, "").toString()

    // --- Password Management (for current user, not password records) ---
    fun savePassword(password: String) {
        prefs.edit {
            putString(KEY_USER_PASSWORD, password)
        }
    }

    fun getPassword(): String = prefs.getString(KEY_USER_PASSWORD, "").toString()

    // --- PIN Management ---
    fun savePin(pin: String) {
        prefs.edit {
            putString(KEY_USER_PIN, pin)
        }
    }

    fun getPin(): String? = prefs.getString(KEY_USER_PIN, null)

    fun hasPin(): Boolean = getPin() != null

    // --- Notification Settings ---
    fun saveNotificationsEnabled(enabled: Boolean) {
        prefs.edit {
            putBoolean(KEY_NOTIFICATIONS_ENABLED, enabled)
        }
    }

    fun getNotificationsEnabled(): Boolean = prefs.getBoolean(KEY_NOTIFICATIONS_ENABLED, false)
}