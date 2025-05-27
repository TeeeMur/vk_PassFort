package com.example.passfort.model.dbentity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.passfort.model.PasswordItem
import com.example.passfort.model.dbutils.DBDateConverters
import com.example.passfort.model.dbutils.DBDateTimeConverters
import java.time.LocalDate
import java.time.LocalDateTime


@Entity(tableName = "password_record")
data class PasswordRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "password_name", index = true) val recordName: String,
    @ColumnInfo(name = "password_login", index = true) val recordLogin: String,
    @ColumnInfo(name = "password_password") var recordPassword: String,
    @ColumnInfo(name = "password_note") var recordNote: String,
    @field:TypeConverters(DBDateTimeConverters::class)
    @ColumnInfo(name = "last_change_at", index = true) val passwordLastChangeDate: LocalDateTime,
    @ColumnInfo(name = "change_interval") val passwordChangeIntervalDays: Int,
    @ColumnInfo(name = "icon_enum") val iconIndex: Int,
    @ColumnInfo(name = "pinned") var pinned: Boolean,
    @field:TypeConverters(DBDateTimeConverters::class)
    @ColumnInfo(name = "last_use") val passwordLastUsedDate: LocalDateTime,

    @ColumnInfo(name = "uri_image") val imageCardUri: String
) {
    fun convertToPasswordItem(): PasswordItem {
        return PasswordItem(
            id = id,
            iconId = iconIndex,
            itemLogin = recordLogin,
            itemName = recordName,
            itemPassword = recordPassword,
            isPinned = pinned,
            imageCardUri = imageCardUri
        )
    }
}
