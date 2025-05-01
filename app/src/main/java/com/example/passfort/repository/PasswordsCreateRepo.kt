package com.example.passfort.repository

import com.example.passfort.model.dbentity.PasswordRecordEntity

interface PasswordsCreateRepo {
    suspend fun upsertPassword(password: PasswordRecordEntity)
}