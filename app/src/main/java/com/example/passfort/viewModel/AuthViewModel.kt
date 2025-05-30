package com.example.passfort.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passfort.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = authRepository.register(name, email, password)
            _isLoading.value = false
            result.onSuccess {

            }.onFailure {

            }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = authRepository.login(username, password)
            _isLoading.value = false
            result.onSuccess {

            }.onFailure {

            }
        }
    }
}