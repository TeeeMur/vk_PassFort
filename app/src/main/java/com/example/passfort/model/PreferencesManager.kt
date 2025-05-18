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


    fun saveAuthState(isLoggedIn: Boolean) {
        sharedPreferences.edit {
            putBoolean("is_user_logged_in", isLoggedIn)
        }
    }
}