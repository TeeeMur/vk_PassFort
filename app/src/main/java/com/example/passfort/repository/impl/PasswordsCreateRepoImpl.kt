package com.example.passfort.repository.impl

import com.example.passfort.model.dbentity.PasswordRecordEntity
import com.example.passfort.model.PassFortDB
import com.example.passfort.repository.PasswordsCreateRepo

class PasswordsCreateRepoImpl(db: PassFortDB): PasswordsCreateRepo {

    val passwordDao = db.getPasswordDao()

    override suspend fun upsertPassword(passwordEntity: PasswordRecordEntity){
        passwordDao.upsertObj(passwordEntity)
    }
}