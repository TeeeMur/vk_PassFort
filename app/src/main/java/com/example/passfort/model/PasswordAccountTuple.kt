package com.example.passfort.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class PasswordAccountTuple(
    val id: Long,
    @ColumnInfo(name = "password_account_name") val passwordAccountName: String,
    @ColumnInfo(name = "password_account_login", index = true) val passwordAccountLogin: String,
    @ColumnInfo(name = "password_account_password") val passwordAccountPassword: String,
)