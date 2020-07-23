package com.rober.simpletodonotes.di.module

import com.rober.simpletodonotes.data.local.NoteDao
import com.rober.simpletodonotes.data.repository.NotesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NoteRepositoryModule {

    @Provides
    @Singleton
    fun provideNotesRepository(noteDao: NoteDao): NotesRepository = NotesRepository(noteDao)
}