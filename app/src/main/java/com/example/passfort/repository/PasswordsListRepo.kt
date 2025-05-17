package com.example.passfort.repository

import com.example.passfort.model.dbentity.PasswordRecordEntity
import kotlinx.coroutines.flow.Flow

interface PasswordsListRepo {
    suspend fun getAllPasswords(): Flow<List<PasswordRecordEntity>>
    suspend fun getPinnedPasswords(): Flow<List<PasswordRecordEntity>>
    suspend fun getNonPinnedPasswords(): List<PasswordRecordEntity>
    suspend fun upsertPassword(password: PasswordRecordEntity)
    suspend fun deletePassword(password: PasswordRecordEntity)
}