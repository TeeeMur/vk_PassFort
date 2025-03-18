package com.example.passfort.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PassFortRepo(private val passwordAccountDao: PasswordAccountDao) {

    suspend fun insertNewPasswordAccount(passwordAccountEntity: PasswordAccountEntity) {
        withContext(Dispatchers.IO) {
            passwordAccountDao.insertNewPasswordAccount(passwordAccountEntity)
        }
    }

    suspend fun getPasswordAccByName(name: String):PasswordAccountTuple {
        return withContext(Dispatchers.IO) {
            return@withContext passwordAccountDao.getPasswordAccountByName(name)
        }
    }

    suspend fun deletePasswordAccByName(name: String) {
        withContext(Dispatchers.IO) {
            passwordAccountDao.deletePasswordAccountByName(name)
        }
    }
}