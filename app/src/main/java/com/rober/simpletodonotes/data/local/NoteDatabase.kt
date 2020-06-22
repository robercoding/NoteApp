package com.rober.simpletodonotes.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rober.simpletodonotes.data.converters.Converters
import com.rober.simpletodonotes.model.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NoteDatabase : RoomDatabase(){

    abstract fun getNoteDao(): NoteDao

    companion object {
        const val DB_NAME = "note_database"

        @Volatile
        private var INSTANCE : NoteDatabase? = null

        fun getInstance(context: Context): NoteDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }else{
                synchronized(this){
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        NoteDatabase::class.java,
                        DB_NAME
                    ).build()

                    INSTANCE = instance
                    return instance
                }
            }
        }

    }
}