package com.example.passfort.repository

import com.example.passfort.dbentity.PasswordRecordEntity
import com.example.passfort.model.PassFortDB

class PasswordsRepoImpl(db: PassFortDB): PasswordsRepo {

    val passwordDao = db.getPasswordDao()

    override suspend fun getAllPasswords(): List<PasswordRecordEntity> {
        return passwordDao.getAll()
    }

    override suspend fun getPinnedPasswords(): List<PasswordRecordEntity> {
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