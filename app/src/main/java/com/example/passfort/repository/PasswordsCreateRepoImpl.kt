package com.example.passfort.repository

import com.example.passfort.model.dbentity.PasswordRecordEntity
import com.example.passfort.model.PassFortDB
import kotlinx.coroutines.flow.Flow

class PasswordsCreateRepoImpl(db: PassFortDB): PasswordsCreateRepo {

    val passwordDao = db.getPasswordDao()

    override suspend fun upsertPassword(passwordEntity: PasswordRecordEntity){
        passwordDao.upsertObj(passwordEntity)
    }
}