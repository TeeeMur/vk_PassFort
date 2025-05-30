package com.example.passfort.repository

import com.example.passfort.model.dbentity.PasswordRecordEntity
import kotlinx.coroutines.flow.Flow

interface PasswordsListRepo {
    fun getAllPasswords(): Flow<List<PasswordRecordEntity>>
    fun getPinnedPasswords(): Flow<List<PasswordRecordEntity>>
    fun getNonPinnedPasswords(): Flow<List<PasswordRecordEntity>>
    suspend fun getPasswordsByName(pattern: String): List<PasswordRecordEntity>
    suspend fun upsertPassword(password: PasswordRecordEntity)
    suspend fun getByIdPassword(id: Long): PasswordRecordEntity
    suspend fun pinPassword(passwordEntity: PasswordRecordEntity)
    suspend fun deletePassword(passwordID: Long)
}