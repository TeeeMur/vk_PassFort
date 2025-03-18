package com.example.passfort.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "password_account")
data class PasswordAccountEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "password_account_name") val passwordAccountName: String,
    @ColumnInfo(name = "password_account_login", index = true) val passwordAccountLogin: String,
    @ColumnInfo(name = "password_account_password") val passwordAccountPassword: String,
)
