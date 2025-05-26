package com.example.passfort.repository

import com.example.passfort.model.dbentity.PasswordRecordEntity

interface PasswordsDetailRepo {
    suspend fun getPassword(id: Long): PasswordRecordEntity
    suspend fun upsertPassword(password: PasswordRecordEntity)
    suspend fun deletePassword(password: PasswordRecordEntity)
}