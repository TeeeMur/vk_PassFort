package com.example.passfort.model.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.passfort.dbentity.PasswordRecordEntity

@Dao
abstract class PasswordDao: BaseDao<PasswordRecordEntity> {

    @Query("SELECT * from password_record")
    abstract suspend fun getAll(): List<PasswordRecordEntity>

    @Query("SELECT * from password_record WHERE pinned = 1")
    abstract suspend fun getPinned(): List<PasswordRecordEntity>

    @Query("SELECT * from password_record WHERE pinned == 0")
    abstract suspend fun getNonPinned(): List<PasswordRecordEntity>

    @Query("SELECT * from password_record WHERE password_name LIKE :name")
    abstract suspend fun getByName(name: String): List<PasswordRecordEntity>
}