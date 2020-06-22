package com.rober.simpletodonotes.data.repository

import com.rober.simpletodonotes.data.local.NoteDao
import com.rober.simpletodonotes.model.Note
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotesRepository @Inject constructor(
    val noteDao: NoteDao
) {
    suspend fun insertDao(note:Note) = noteDao.insert(note)

    fun getAllNotes() = noteDao.getAllNotes()
}