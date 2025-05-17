package com.example.passfort.repository

import com.example.passfort.model.dbentity.PasswordRecordEntity
import kotlinx.collections.immutable.ImmutableList

interface PasswordsListRepo {
    suspend fun getAllPasswords(): ImmutableList<PasswordRecordEntity>
    suspend fun getPinnedPasswords(): ImmutableList<PasswordRecordEntity>
    suspend fun getNonPinnedPasswords(): ImmutableList<PasswordRecordEntity>
    suspend fun upsertPassword(password: PasswordRecordEntity)
    suspend fun deletePassword(password: PasswordRecordEntity)
}