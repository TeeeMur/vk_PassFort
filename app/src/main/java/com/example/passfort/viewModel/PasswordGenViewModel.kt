package com.example.passfort.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class PasswordGenViewModel @Inject constructor() : ViewModel() {
    var enableCharacters: MutableStateFlow<Boolean>
}