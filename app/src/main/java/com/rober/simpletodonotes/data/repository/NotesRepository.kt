package com.rober.simpletodonotes.data.repository

import com.rober.simpletodonotes.data.local.NoteDao
import com.rober.simpletodonotes.model.Note
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotesRepository @Inject constructor(
    private val noteDao: NoteDao
) {
    suspend fun insert(note:Note) = noteDao.insert(note)

    suspend fun deleteNote(note:Note) = noteDao.delete(note)

    fun getAllNotes() = noteDao.getAllNotes()
}