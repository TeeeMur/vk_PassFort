package com.example.passfort.repository

import com.example.passfort.model.dbentity.PasswordRecordEntity
import com.example.passfort.model.PassFortDB

class PasswordsDetailRepoImpl(db: PassFortDB): PasswordsDetailRepo {

    val passwordDao = db.getPasswordDao()

    override suspend fun getPassword(id: Int): PasswordRecordEntity {
        return passwordDao.getById(id)
    }

    override suspend fun upsertPassword(passwordEntity: PasswordRecordEntity){
        passwordDao.upsertObj(passwordEntity)
    }

    override suspend fun deletePassword(passwordEntity: PasswordRecordEntity) {
        passwordDao.deleteObj(passwordEntity)
    }
}