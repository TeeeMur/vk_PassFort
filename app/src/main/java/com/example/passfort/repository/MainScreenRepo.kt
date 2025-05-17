package com.example.passfort.repository

import com.example.passfort.model.dbentity.PasswordRecordEntity
import kotlinx.coroutines.flow.Flow

interface MainScreenRepo {
    fun getPinnedPasswords(): Flow<List<PasswordRecordEntity>>
    fun getRecentPasswords(count: Int): Flow<List<PasswordRecordEntity>>
}