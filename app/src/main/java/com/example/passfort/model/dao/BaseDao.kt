package com.example.passfort.model.dao

import androidx.room.Delete
import androidx.room.Upsert

interface BaseDao<T> {

    @Upsert
    suspend fun upsertObj(obj: T)

    @Delete
    suspend fun deleteObj(obj: T)
}