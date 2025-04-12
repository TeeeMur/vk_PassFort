package com.example.passfort.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import java.security.SecureRandom

@HiltViewModel
class GeneratorViewModel @Inject constructor() : ViewModel() {
    private var _enableLowercaseCharacters: MutableStateFlow<Boolean> = MutableStateFlow(true)
    private var _enableUppercaseCharacters: MutableStateFlow<Boolean> = MutableStateFlow(true)
    private var _enableDigits: MutableStateFlow<Boolean> = MutableStateFlow(true)
    private var _enableSpecSymbols: MutableStateFlow<Boolean> = MutableStateFlow(true)
    private var _passwordLength: MutableStateFlow<Int> = MutableStateFlow(64)
    private var _password: MutableStateFlow<String> = MutableStateFlow("")
    val enableLowercaseCharacters: StateFlow<Boolean> = _enableLowercaseCharacters.asStateFlow()
    val enableUppercaseCharacters: StateFlow<Boolean> = _enableUppercaseCharacters.asStateFlow()
    val enableDigits: StateFlow<Boolean> = _enableDigits.asStateFlow()
    val enableSpecSymbols: StateFlow<Boolean> = _enableSpecSymbols.asStateFlow()
    val passwordLength: StateFlow<Int> = _passwordLength.asStateFlow()
    val password: StateFlow<String> = _password.asStateFlow()

    fun setLowercaseCharacters() {
        _enableLowercaseCharacters.update { !it }
        if (!(_enableUppercaseCharacters.value or
                    _enableLowercaseCharacters.value or _enableDigits.value or _enableSpecSymbols.value)) {
            setDigits()
        }
        else generatePassword()
    }

    fun setUppercaseCharacters() {
        _enableUppercaseCharacters.update { !it }
        if (!(_enableUppercaseCharacters.value or
            _enableLowercaseCharacters.value or _enableDigits.value or _enableSpecSymbols.value)) {
            setDigits()
        }
        else generatePassword()
    }

    fun setDigits() {
        _enableDigits.update { !it }
        if (!(_enableUppercaseCharacters.value or
                    _enableLowercaseCharacters.value or _enableDigits.value or _enableSpecSymbols.value)) {
            setLowercaseCharacters()
        }
        else generatePassword()
    }

    fun setSpecSymbols() {
        _enableSpecSymbols.update { !it }
        if (!(_enableUppercaseCharacters.value or
                    _enableLowercaseCharacters.value or _enableDigits.value or _enableSpecSymbols.value)) {
            setDigits()
        }
        else generatePassword()
    }

    fun setPasswordLength(length: Int) {
        _passwordLength.update { length }
        generatePassword()
    }

    fun generatePassword() {
        var charsChooseSet = ""
        if (_enableDigits.value) charsChooseSet += DIGITS
        if (_enableSpecSymbols.value) charsChooseSet += SPEC_CHARS
        if (_enableLowercaseCharacters.value) charsChooseSet += LOWERCASE_CHARS
        if (_enableUppercaseCharacters.value) charsChooseSet += UPPERCASE_CHARS
        _password.update { (1.._passwordLength.value).map{ charsChooseSet[randomGenerator.nextInt(charsChooseSet.length)] }.joinToString("") }
    }

    companion object {
        private const val LOWERCASE_CHARS = "abcdefghijklmnopqrstuvwxyz"
        private const val UPPERCASE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        private const val DIGITS = "01234567890"
        private const val SPEC_CHARS = "!@#$%^&*()"
        private val randomGenerator = SecureRandom()
    }
}