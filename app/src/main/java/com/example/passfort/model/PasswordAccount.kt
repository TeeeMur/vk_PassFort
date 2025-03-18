package com.example.passfort.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import androidx.room.Room

data class PasswordAccount(
    val passwordAccountName: String,
    val passwordAccountLogin: String,
    val passwordAccountPassword: String,
) {
    fun toPasswordAccountEntity(): PasswordAccountEntity = PasswordAccountEntity(
        passwordAccountName = passwordAccountName,
        passwordAccountLogin = passwordAccountLogin,
        passwordAccountPassword = passwordAccountPassword
    )
}