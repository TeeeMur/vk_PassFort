package com.example.passfort.repository.impl

import com.example.passfort.model.PassFortDB
import com.example.passfort.model.dbentity.PasswordRecordEntity
import com.example.passfort.repository.MainScreenRepo

class MainScreenRepoImpl(db: PassFortDB): MainScreenRepo {
    val passwordDao = db.getPasswordDao()
    override suspend fun getPinnedPasswords(): List<PasswordRecordEntity> {
        return passwordDao.getPinned()
    }

    override suspend fun getRecentPasswords(): List<PasswordRecordEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getRecentPasswords(count: Int): List<PasswordRecordEntity> {
        return passwordDao.getRecent(count)
    }

}