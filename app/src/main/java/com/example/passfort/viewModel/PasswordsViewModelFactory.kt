package com.example.passfort.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.passfort.repository.PasswordsRepoImpl
import kotlin.jvm.java

class PasswordsViewModelFactory(val repo: PasswordsRepoImpl): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == PasswordsViewModel::class.java) {
            return PasswordsViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}