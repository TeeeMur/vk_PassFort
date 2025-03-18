package com.example.passfort.model

import androidx.room.Room
import android.content.Context

object Dependencies {

    private lateinit var applicationContext: Context

    fun init(context: Context) {
        applicationContext = context
    }

    private val appDatabase: PassFortDB by lazy {
        Room.databaseBuilder(applicationContext, PassFortDB::class.java, "database.db")
            .build()
    }

    val passwordRepo: PassFortRepo by lazy { PassFortRepo(appDatabase.getPasswordAccountDao())}

}