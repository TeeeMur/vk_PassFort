package com.example.passfort.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Состояния экрана PIN-кода
sealed class PinCodeState {
    object Input   : PinCodeState()
    object Loading : PinCodeState()
    object Success : PinCodeState()
    data class Error(val message: String) : PinCodeState()
}

class PinCodeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<PinCodeState>(PinCodeState.Input)
    val uiState: StateFlow<PinCodeState> = _uiState

    private val _pin = MutableStateFlow("")
    val pin: StateFlow<String> = _pin


    private val requiredPinLength = 5

    fun onDigitClick(digit: Char) {
        if (_pin.value.length < requiredPinLength) {
            _pin.value += digit
        }
    }

    fun onDelete() {
        if (_pin.value.isNotEmpty()) {
            _pin.value = _pin.value.dropLast(1)
        }
    }

    fun verifyPin(entered: String, correct: String) {
        _uiState.value = PinCodeState.Loading
        viewModelScope.launch {
            delay(1500)
            if (entered == correct) {
                _uiState.value = PinCodeState.Success
            } else {
                _uiState.value = PinCodeState.Error("Неверный PIN-код")
            }
        }
    }

    fun resetState() {
        _pin.value = ""
        _uiState.value = PinCodeState.Input
    }


    fun retry() {
        resetState()
    }
}
