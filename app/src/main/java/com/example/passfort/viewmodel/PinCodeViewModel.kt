package com.example.passfort.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Определяем состояния экрана ПИН-кода
sealed class PinCodeState {
    object Input : PinCodeState()            // Пользователь вводит ПИН
    object Loading : PinCodeState()          // Проверяется ПИН-код (загрузка)
    data class Error(val message: String) : PinCodeState() // Ошибка при проверке
}

class PinCodeViewModel : ViewModel() {

    // Состояние экрана
    private val _uiState = MutableStateFlow<PinCodeState>(PinCodeState.Input)
    val uiState: StateFlow<PinCodeState> = _uiState

    // Текущий введённый ПИН-код
    private val _pin = MutableStateFlow("")
    val pin: StateFlow<String> = _pin

    // Необходимая длина ПИН-кода (например, 5)
    private val requiredPinLength = 5

    // Функция обработки нажатия цифры
    fun onDigitClick(digit: Char) {
        if (_pin.value.length < requiredPinLength) {
            _pin.value += digit
            if (_pin.value.length == requiredPinLength) {
                checkPin(_pin.value)
            }
        }
    }

    // Удаление последней цифры
    fun onDelete() {
        if (_pin.value.isNotEmpty()) {
            _pin.value = _pin.value.dropLast(1)
        }
    }

    // Имитация проверки ПИН-кода
    private fun checkPin(enteredPin: String) {
        _uiState.value = PinCodeState.Loading
        viewModelScope.launch {
            delay(1500) // имитируем задержку проверки
            if (enteredPin == "1234") {
                // Если ПИН правильный, сбрасываем состояние (можно перейти на другой экран)
                _pin.value = ""
                _uiState.value = PinCodeState.Input
            } else {
                _uiState.value = PinCodeState.Error("Неверный ПИН-код")
            }
        }
    }

    // Функция для перехода из состояния ошибки обратно к вводу
    fun retry() {
        _pin.value = ""
        _uiState.value = PinCodeState.Input
    }
}
