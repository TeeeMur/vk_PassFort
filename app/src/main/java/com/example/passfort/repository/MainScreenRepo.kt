package com.example.passfort.repository

import com.example.passfort.model.dbentity.PasswordRecordEntity
import kotlinx.coroutines.flow.Flow

interface MainScreenRepo {
    suspend fun getPinnedPasswords(): List<PasswordRecordEntity>
    suspend fun getRecentPasswords(count: Int): List<PasswordRecordEntity>
}