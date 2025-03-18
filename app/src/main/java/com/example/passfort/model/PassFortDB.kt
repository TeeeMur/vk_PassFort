package com.example.passfort.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 2,
    entities = [
        PasswordAccountEntity::class
    ]
)
abstract class PassFortDB : RoomDatabase(){

    abstract fun getPasswordAccountDao(): PasswordAccountDao

}