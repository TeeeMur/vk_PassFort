package com.example.passfort.model.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.passfort.dbentity.TOTPRecordEntity

@Dao
abstract class TOTPDao: BaseDao<TOTPRecordEntity> {

    @Query("SELECT * from totp_record")
    abstract suspend fun getAll(): List<TOTPRecordEntity>
}