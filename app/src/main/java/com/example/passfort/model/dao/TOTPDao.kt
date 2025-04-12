package com.example.passfort.model.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.passfort.dbentity.TOTPRecordEntity

@Dao
interface TOTPDao: BaseDao<TOTPRecordEntity> {

    @Query("SELECT * from totp_record")
    suspend fun getAll(): List<TOTPRecordEntity>
}