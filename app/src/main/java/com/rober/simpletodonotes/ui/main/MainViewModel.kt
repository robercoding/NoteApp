package com.rober.simpletodonotes.ui.main


import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rober.simpletodonotes.data.repository.NotesRepository
import com.rober.simpletodonotes.model.Note
import com.rober.simpletodonotes.ui.main.state.NoteInteractionManager
import com.rober.simpletodonotes.ui.main.state.NoteToolbarState
import com.rober.simpletodonotes.util.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

const val TAG = "MainViewModel"
class MainViewModel @ViewModelInject constructor(
    private val noteRepository: NotesRepository
) : ViewModel() {

    private var _eventNotes = MutableLiveData<Event<ArrayList<Note>>>()

    val eventNotes: LiveData<Event<ArrayList<Note>>>
        get() = _eventNotes

    val toolbarState: LiveData<NoteToolbarState>
        get() = noteInteractionManager.toolbarState

    val noteInteractionManager : NoteInteractionManager = NoteInteractionManager()

    val notes = noteRepository.getAllNotes()

    fun setToolbarState(toolbarState : NoteToolbarState){
        noteInteractionManager.setToolbarState(toolbarState)
    }

    fun isMultiSelectionActivated() =
        noteInteractionManager.isMultiSelectionActivated()

    fun addOrRemoveNoteFromSelectedList(note: Note){
        noteInteractionManager.addOrRemoveNoteFromSelectedList(note)
    }

    fun insertNote(note: Note){
        viewModelScope.launch {
            noteRepository.insert(note)
        }
    }

    fun deleteNote(note: Note){
        var deletedNote: ArrayList<Note> = ArrayList()
        viewModelScope.launch {
            val id = noteRepository.deleteNote(note)
            if(id>0){
                deletedNote.add(note)
                _eventNotes.value = Event.Delete("Note has been deleted", deletedNote)
            }else{
                _eventNotes.value = Event.ErrorDelete("Note couldn't be deleted")
            }
        }
    }

    fun deleteSelectedNotes(){
        val notesToDelete = noteInteractionManager.getSelectedNotes().iterator()
        var deletedNotes: ArrayList<Note> = ArrayList()

        viewModelScope.launch {
            while(notesToDelete.hasNext()){
                val note = notesToDelete.next()
                val id = noteRepository.deleteNote(note)
                if(id > 0){
                    deletedNotes.add(note)
                }
                //addOrRemoveNoteFromSelectedList(note) BUG
            }
            if(deletedNotes.size > 0){
                _eventNotes.value = Event.Delete("${deletedNotes.size} notes has been deleted", deletedNotes)
            }
        }

    }
}