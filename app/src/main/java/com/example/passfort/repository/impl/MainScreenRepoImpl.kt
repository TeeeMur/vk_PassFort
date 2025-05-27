package com.example.passfort.repository.impl

import com.example.passfort.model.PassFortDB
import com.example.passfort.model.dbentity.PasswordRecordEntity
import com.example.passfort.repository.MainScreenRepo
import kotlinx.coroutines.flow.Flow

class MainScreenRepoImpl(db: PassFortDB): MainScreenRepo {
    val passwordDao = db.getPasswordDao()
    override fun getPinnedPasswords(): Flow<List<PasswordRecordEntity>> {
        return passwordDao.getPinned()
    }

    override fun getRecentPasswords(count: Int): Flow<List<PasswordRecordEntity>> {
        return passwordDao.getRecent(count)
    }

    override suspend fun getByIdPassword(id: Long): PasswordRecordEntity{
        return passwordDao.getById(id)
    }

    override suspend fun pinPassword(passwordEntity: PasswordRecordEntity){
        passwordDao.upsertObj(passwordEntity)
    }

    override suspend fun deletePassword(passwordID:Long) {
        passwordDao.deleteById(passwordID)
    }
}