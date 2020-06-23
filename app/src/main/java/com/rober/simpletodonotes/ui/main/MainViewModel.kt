package com.rober.simpletodonotes.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rober.simpletodonotes.data.repository.NotesRepository
import com.rober.simpletodonotes.model.Note
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    val noteRepository: NotesRepository
) : ViewModel() {

    private val _notesLiveData = MutableLiveData<List<Note>>()
    val notes = noteRepository.getAllNotes()

    val notesLiveData: LiveData<List<Note>>
        get() = _notesLiveData

    fun insertNote(note: Note){
        viewModelScope.launch {
            noteRepository.insertDao(note)
        }
    }
}