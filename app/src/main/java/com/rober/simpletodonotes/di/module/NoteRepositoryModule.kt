package com.rober.simpletodonotes.di.module

import com.rober.simpletodonotes.data.local.NoteDao
import com.rober.simpletodonotes.data.repository.NotesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
class NoteRepositoryModule {

    @Provides
    @ActivityRetainedScoped
    fun provideNotesRepository(noteDao: NoteDao): NotesRepository = NotesRepository(noteDao)
}