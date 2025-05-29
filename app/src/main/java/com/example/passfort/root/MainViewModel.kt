package com.example.passfort.root

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passfort.model.PreferencesManager
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _isUserLoggedIn = MutableStateFlow(firebaseAuth.currentUser != null || preferencesManager.isUserLoggedIn())
    val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn.asStateFlow()

    init {
        viewModelScope.launch {
            val authStateFlow = callbackFlow {
                val listener = FirebaseAuth.AuthStateListener { auth ->
                    val loggedIn = auth.currentUser != null
                    trySend(loggedIn)
                }
                firebaseAuth.addAuthStateListener(listener)
                awaitClose {
                    firebaseAuth.removeAuthStateListener(listener)
                }
            }

            authStateFlow.collect { firebaseIsLoggedIn ->
                if (_isUserLoggedIn.value != firebaseIsLoggedIn) {
                    _isUserLoggedIn.value = firebaseIsLoggedIn
                }
                if (preferencesManager.isUserLoggedIn() != firebaseIsLoggedIn) {
                    preferencesManager.setUserLoggedIn(firebaseIsLoggedIn)
                }
            }
        }

        val currentFirebaseUser = firebaseAuth.currentUser
        val storedLoginState = preferencesManager.isUserLoggedIn()

        if (currentFirebaseUser != null && !storedLoginState) {
            preferencesManager.setUserLoggedIn(true)
            if (!_isUserLoggedIn.value) _isUserLoggedIn.value = true
        } else if (currentFirebaseUser == null && storedLoginState) {
            preferencesManager.setUserLoggedIn(false)
            if (_isUserLoggedIn.value) _isUserLoggedIn.value = false
        }
    }

    fun login() {
        Timber.tag("MainViewModel").d("login() callback. Current state: ${_isUserLoggedIn.value}")
    }

    fun logout() {
        firebaseAuth.signOut()
        preferencesManager.setUserLoggedIn(false)
        Timber.tag("MainViewModel").d("logout() called. Firebase signOut initiated.")
    }
}