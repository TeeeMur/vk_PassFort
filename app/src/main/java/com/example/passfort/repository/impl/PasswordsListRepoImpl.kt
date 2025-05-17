package com.example.passfort.repository.impl

import com.example.passfort.model.dbentity.PasswordRecordEntity
import com.example.passfort.model.PassFortDB
import com.example.passfort.repository.PasswordsListRepo
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

class PasswordsListRepoImpl(db: PassFortDB): PasswordsListRepo {

    val passwordDao = db.getPasswordDao()

    override suspend fun getAllPasswords(): ImmutableList<PasswordRecordEntity> {
        return passwordDao.getAll().toImmutableList()
    }

    override suspend fun getPinnedPasswords(): ImmutableList<PasswordRecordEntity> {
        return passwordDao.getPinned().toImmutableList()
    }

    override suspend fun getNonPinnedPasswords(): ImmutableList<PasswordRecordEntity> {
        return passwordDao.getNonPinned().toImmutableList()
    }

    override suspend fun upsertPassword(passwordEntity: PasswordRecordEntity){
        passwordDao.upsertObj(passwordEntity)
    }

    override suspend fun deletePassword(passwordEntity: PasswordRecordEntity) {
        passwordDao.deleteObj(passwordEntity)
    }
}