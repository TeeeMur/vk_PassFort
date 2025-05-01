package com.example.passfort.model.dbentity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "note_record")
data class NoteRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "note_name") val noteName: String,
    @ColumnInfo(name = "note_text") val noteText: String,
)
