package com.example.passfort.model.dbentity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "totp_record")
data class TOTPRecordEntity(
    @PrimaryKey val id: Long = 0,
    @ColumnInfo(name = "secret_code") val secretCode: ByteArray,
    @ColumnInfo(name = "icon_enum") val iconIndex: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TOTPRecordEntity

        if (id != other.id) return false
        if (!secretCode.contentEquals(other.secretCode)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + secretCode.contentHashCode()
        return result
    }
}
