package com.rober.simpletodonotes.data.converters

import androidx.room.TypeConverter
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Converters {

    @TypeConverter
    fun fromDate(date:Date) : String?{
        val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yy")
        return if(date==null) null else dateFormat.format(date)
    }

    @TypeConverter
    fun fromString(string:String) : Date?{
        return if(string==null) null else SimpleDateFormat("dd/MM/yyyy").parse(string)
    }
}