package com.example.passfort.model.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.passfort.dbentity.NoteRecordEntity

@Dao
abstract class NoteDao: BaseDao<NoteRecordEntity> {

    @Query("SELECT * from note_record")
    abstract suspend fun getAll(): List<NoteRecordEntity>
}