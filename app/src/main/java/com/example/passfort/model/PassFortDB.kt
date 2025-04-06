package com.example.passfort.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.passfort.dbentity.*
import com.example.passfort.model.dao.NoteDao
import com.example.passfort.model.dao.PasswordDao
import com.example.passfort.model.dao.TOTPDao

@Database(
    version = 3,
    entities = [
        PasswordRecordEntity::class,
        NoteRecordEntity::class,
        TOTPRecordEntity::class
    ]
)
abstract class PassFortDB : RoomDatabase(){

    abstract fun getPasswordDao(): PasswordDao
    abstract fun getTOTPDao(): TOTPDao
    abstract fun getNoteRecordDao(): NoteDao

}