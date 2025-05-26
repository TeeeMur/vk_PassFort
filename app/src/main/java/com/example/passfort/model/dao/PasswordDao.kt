package com.example.passfort.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.example.passfort.model.dbentity.PasswordRecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordDao: BaseDao<PasswordRecordEntity> {

    @Query("SELECT * from password_record")
    fun getAll(): Flow<List<PasswordRecordEntity>>

    @Query("SELECT * from password_record WHERE pinned = 1")
    fun getPinned(): Flow<List<PasswordRecordEntity>>

    @Query("SELECT * from password_record WHERE pinned == 0")
    fun getNonPinned(): Flow<List<PasswordRecordEntity>>

    @Query("SELECT * from password_record ORDER BY last_use LIMIT :count")
    fun getRecent(count: Int): Flow<List<PasswordRecordEntity>>

    @Query("SELECT * from password_record WHERE password_name LIKE :name")
    suspend fun getByName(name: String): List<PasswordRecordEntity>

    @Query("SELECT * from password_record WHERE id LIKE :id")
    suspend fun getById(id: Long): PasswordRecordEntity

    @Query("DELETE FROM password_record WHERE id = :id")
    suspend fun deleteById(id: Long)
}