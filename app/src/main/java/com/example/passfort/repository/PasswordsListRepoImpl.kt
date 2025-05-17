package com.example.passfort.repository

import com.example.passfort.model.dbentity.PasswordRecordEntity
import com.example.passfort.model.PassFortDB
import kotlinx.coroutines.flow.Flow

class PasswordsListRepoImpl(db: PassFortDB): PasswordsListRepo {

    val passwordDao = db.getPasswordDao()

    override suspend fun getAllPasswords(): Flow<List<PasswordRecordEntity>> {
        return passwordDao.getAll()
    }

    override suspend fun getPinnedPasswords(): Flow<List<PasswordRecordEntity>> {
        return passwordDao.getPinned()
    }

    override suspend fun getNonPinnedPasswords(): List<PasswordRecordEntity> {
        return passwordDao.getNonPinned()
    }

    override suspend fun upsertPassword(passwordEntity: PasswordRecordEntity){
        passwordDao.upsertObj(passwordEntity)
    }

    override suspend fun deletePassword(passwordEntity: PasswordRecordEntity) {
        passwordDao.deleteObj(passwordEntity)
    }
}