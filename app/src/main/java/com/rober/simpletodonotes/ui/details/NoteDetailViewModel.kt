package com.rober.simpletodonotes.ui.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.rober.simpletodonotes.data.repository.NotesRepository

class NoteDetailViewModel @ViewModelInject constructor(private val repository: NotesRepository)
    : ViewModel() {
}