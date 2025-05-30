package com.example.passfort.repository.impl

import com.example.passfort.model.PassFortDB
import com.example.passfort.model.dbentity.PasswordRecordEntity
import com.example.passfort.repository.PasswordsListRepo
import kotlinx.coroutines.flow.Flow

class PasswordsListRepoImpl(db: PassFortDB): PasswordsListRepo {

    val passwordDao = db.getPasswordDao()

    override fun getAllPasswords(): Flow<List<PasswordRecordEntity>> {
        return passwordDao.getAll()
    }

    override fun getPinnedPasswords(): Flow<List<PasswordRecordEntity>> {
        return passwordDao.getPinned()
    }

    override fun getNonPinnedPasswords(): Flow<List<PasswordRecordEntity>> {
        return passwordDao.getNonPinned()
    }

    override suspend fun getPasswordsByName(pattern: String): List<PasswordRecordEntity> {
        return passwordDao.getByName(pattern)
    }

    override suspend fun upsertPassword(passwordEntity: PasswordRecordEntity) {
        passwordDao.upsertObj(passwordEntity)
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