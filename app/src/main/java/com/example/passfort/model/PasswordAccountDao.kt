package com.example.passfort.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PasswordAccountDao {

    @Insert(entity = PasswordAccountEntity::class)
    fun insertNewPasswordAccount(passwordAccountEntity: PasswordAccountEntity)

    @Query("SELECT * FROM password_account WHERE password_account_name = :name;")
    fun getPasswordAccountByName(name: String): PasswordAccountTuple

    @Query("DELETE FROM password_account WHERE password_account_name = :passwordAccountName;")
    fun deletePasswordAccountByName(passwordAccountName: String)
}