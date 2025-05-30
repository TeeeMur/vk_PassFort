package com.example.passfort.viewModel

import androidx.lifecycle.ViewModel

abstract class EditPasswordViewModel: ViewModel() {
    abstract fun onPasswordChange(newPassword: String)
}