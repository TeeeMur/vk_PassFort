package com.example.passfort.repository

import com.example.passfort.model.dbentity.PasswordRecordEntity

interface MainScreenRepo {
    suspend fun getPinnedPasswords(): List<PasswordRecordEntity>
    suspend fun getRecentPasswords(count: Int): List<PasswordRecordEntity>
}