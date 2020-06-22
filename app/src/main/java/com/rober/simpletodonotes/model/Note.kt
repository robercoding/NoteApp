package com.rober.simpletodonotes.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.rober.simpletodonotes.data.converters.Converters
import com.rober.simpletodonotes.model.Note.Companion.TABLE_NAME
import java.util.*

@Entity(tableName = TABLE_NAME)
data class Note(

    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,

    var text: String? = null,

    @TypeConverters(Converters::class)
    var date: Date? = null

) {
    companion object{
        const val TABLE_NAME = "table_notes"
    }
}