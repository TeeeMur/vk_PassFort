package com.example.passfort.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passfort.model.PassFortRepo
import com.example.passfort.model.PasswordAccount
import com.example.passfort.model.PasswordAccountTuple
import kotlinx.coroutines.launch

class PassFortViewModel(private val passwordRepo: PassFortRepo): ViewModel() {

    val currPassword = MutableLiveData<PasswordAccountTuple>()

    fun insertNewPassword(name: String, login: String, password: String) {
        viewModelScope.launch {
            passwordRepo.insertNewPasswordAccount(
                PasswordAccount(
                    passwordAccountName = name,
                    passwordAccountLogin = login,
                    passwordAccountPassword = password,
                ).toPasswordAccountEntity()
            )
        }
    }

    fun getPasswordByName(name: String) {
        viewModelScope.launch {
            currPassword.value = passwordRepo.getPasswordAccByName(name)
        }
    }

}