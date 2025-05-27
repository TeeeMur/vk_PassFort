package com.example.passfort.model

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesManager @Inject constructor(@ApplicationContext context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("passfort_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_IS_USER_LOGGED_IN = "is_user_logged_in"
        private const val KEY_USER_PIN = "user_pin" // Added a constant for the PIN key
    }

    fun setUserLoggedIn(isLoggedIn: Boolean) {
        sharedPreferences.edit {
            putBoolean(KEY_IS_USER_LOGGED_IN, isLoggedIn)
        }
    }

    fun isUserLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_USER_LOGGED_IN, false)
    }

    fun clearLoginState() {
        sharedPreferences.edit {
            remove(KEY_IS_USER_LOGGED_IN)
        }
    }

    // This method is redundant with setUserLoggedIn, consider removing one.
    // I'm keeping it for now to match your original structure, but renamed the key for clarity.
    fun saveAuthState(isLoggedIn: Boolean) {
        sharedPreferences.edit {
            putBoolean(KEY_IS_USER_LOGGED_IN, isLoggedIn) // Using the existing key for consistency
        }
    }

    fun clearAuthState() {
        sharedPreferences.edit { clear() } // Use sharedPreferences
    }

    // This method is redundant with the first isUserLoggedIn, consider removing one.
    // I'm keeping it for now to match your original structure.
    fun isUserLoggedInAuthCheck(): Boolean = sharedPreferences.getBoolean(KEY_IS_USER_LOGGED_IN, false)

    fun savePin(pin: String) {
        sharedPreferences.edit {
            putString(KEY_USER_PIN, pin) // Using the new constant for PIN key
        }
    }

    fun getPin(): String? = sharedPreferences.getString(KEY_USER_PIN, null) // Using the new constant for PIN key

    fun hasPin(): Boolean = getPin() != null
}