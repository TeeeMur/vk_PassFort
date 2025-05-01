package com.example.passfort.model.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.passfort.model.dbentity.PasswordRecordEntity

@Dao
interface PasswordDao: BaseDao<PasswordRecordEntity> {

    @Query("SELECT * from password_record")
    suspend fun getAll(): List<PasswordRecordEntity>

    @Query("SELECT * from password_record WHERE pinned = 1")
    suspend fun getPinned(): List<PasswordRecordEntity>

    @Query("SELECT * from password_record WHERE pinned == 0")
    suspend fun getNonPinned(): List<PasswordRecordEntity>

    @Query("SELECT * from password_record WHERE password_name LIKE :name")
    suspend fun getByName(name: String): List<PasswordRecordEntity>
}