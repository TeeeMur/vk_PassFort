package com.example.passfort.repository

import com.example.passfort.model.dbentity.PasswordRecordEntity

interface PasswordsListRepo {
    suspend fun getAllPasswords(): List<PasswordRecordEntity>
    suspend fun getPinnedPasswords(): List<PasswordRecordEntity>
    suspend fun getNonPinnedPasswords(): List<PasswordRecordEntity>
    suspend fun upsertPassword(password: PasswordRecordEntity)
    suspend fun deletePassword(password: PasswordRecordEntity)
}