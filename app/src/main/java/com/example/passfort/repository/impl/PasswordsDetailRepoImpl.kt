package com.example.passfort.repository.impl

import com.example.passfort.model.dbentity.PasswordRecordEntity
import com.example.passfort.model.PassFortDB
import com.example.passfort.repository.PasswordsDetailRepo

class PasswordsDetailRepoImpl(db: PassFortDB): PasswordsDetailRepo {

    val passwordDao = db.getPasswordDao()

    override suspend fun getPassword(id: Long): PasswordRecordEntity {
        return passwordDao.getById(id)
    }

    override suspend fun upsertPassword(passwordEntity: PasswordRecordEntity){
        passwordDao.upsertObj(passwordEntity)
    }

    override suspend fun deletePassword(passwordEntity: PasswordRecordEntity) {
        passwordDao.deleteObj(passwordEntity)
    }
}