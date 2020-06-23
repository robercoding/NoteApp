package com.rober.simpletodonotes.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rober.simpletodonotes.model.Note
import com.rober.simpletodonotes.model.Note.Companion.TABLE_NAME

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note): Long

    @Update
    suspend fun update(note: Note): Int

    @Delete
    suspend fun delete(note: Note): Int

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun deleteAllNotes(): Int
}