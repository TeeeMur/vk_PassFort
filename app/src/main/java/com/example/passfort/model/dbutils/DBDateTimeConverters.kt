package com.example.passfort.model.dbutils

import androidx.room.TypeConverter
import java.time.LocalDateTime

class DBDateTimeConverters {

    @TypeConverter
    fun dateTimeToString(dateTime: LocalDateTime): String = dateTime.toString()

    @TypeConverter
    fun stringToDateTime(dateTimeString: String): LocalDateTime = LocalDateTime.parse(dateTimeString)

}
