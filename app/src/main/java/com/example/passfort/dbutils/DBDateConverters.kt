package com.example.passfort.dbutils

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DBDateConverters {

    @TypeConverter
    fun timeStringToLocalDate(timeString: String): LocalDate =
        LocalDate.parse(timeString, DateTimeFormatter.ISO_LOCAL_DATE)

    @TypeConverter
    fun dateToTimeString(date: LocalDate): String = date.toString()

}