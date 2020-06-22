package com.rober.simpletodonotes.di.module

import android.app.Application
import com.rober.simpletodonotes.data.local.NoteDao
import com.rober.simpletodonotes.data.local.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NoteDatabaseModule {

    @Singleton
    @Provides
    fun provideNoteDatabase(application: Application): NoteDatabase = NoteDatabase.getInstance(application)

    @Singleton
    @Provides
    fun provideNoteDao(database: NoteDatabase): NoteDao = database.getNoteDao()
}