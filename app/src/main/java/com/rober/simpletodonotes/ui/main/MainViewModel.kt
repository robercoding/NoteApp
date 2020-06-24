package com.rober.simpletodonotes.ui.main


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rober.simpletodonotes.data.repository.NotesRepository
import com.rober.simpletodonotes.model.Note
import com.rober.simpletodonotes.util.Event
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val noteRepository: NotesRepository
) : ViewModel() {
    private var _event = MutableLiveData<Event<Note>>()
    val event : LiveData<Event<Note>>
        get() = _event


    val notes = noteRepository.getAllNotes()
    //private val _notesLiveData = MutableLiveData<List<Note>>()
    /*val notesLiveData: LiveData<List<Note>>
        get() = _notesLiveData*/

    fun insertNote(note: Note){
        viewModelScope.launch {
            noteRepository.insert(note)
        }
    }

    fun deleteNote(note: Note){
        viewModelScope.launch {
            val id = noteRepository.deleteNote(note)
            if(id>0){
                _event.value = Event.Delete("Note has been deleted", note)
            }else{
                _event.value = Event.ErrorDelete("Note couldn't be deleted")
            }
        }
    }
}