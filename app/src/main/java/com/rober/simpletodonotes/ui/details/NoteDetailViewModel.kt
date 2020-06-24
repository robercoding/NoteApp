package com.rober.simpletodonotes.ui.details

import android.util.Log
import android.widget.Toast
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import com.rober.simpletodonotes.data.repository.NotesRepository
import com.rober.simpletodonotes.model.Note
import com.rober.simpletodonotes.util.Event
import com.rober.simpletodonotes.util.EventMessage
import kotlinx.coroutines.launch
import java.util.*

const val TAG = "NoteDetailViewModel"
class NoteDetailViewModel @ViewModelInject constructor(private val repository: NotesRepository)
    : ViewModel(), Observable {

    private lateinit var note: Note
    private var _eventMessage = MutableLiveData<EventMessage<String>>()
    private var _eventAction = MutableLiveData<Event<Note>>()

    val eventMessage : LiveData<EventMessage<String>>
        get() = _eventMessage
    val eventAction : LiveData<Event<Note>>
            get() = _eventAction

    private var updateNote: Boolean = false

    @Bindable
    val inputTitle = MutableLiveData<String>()
    @Bindable
    val inputText = MutableLiveData<String>()

    fun initUpdate(note: Note){
        inputTitle.value = note.title
        inputText.value = note.text
        this.note = note
        this.updateNote = true
    }

    fun initCreate(){
        this.updateNote = false
    }

    fun saveNote() {
        if(checkFieldValues()){
            if(updateNote){
                note.title = inputTitle.value
                note.text = inputText.value
                viewModelScope.launch {
                    val id = repository.insert(note)
                    if(ifInserted(id)){
                        clearFieldValues()
                        _eventAction.value = Event.Update("Note has been updated!")
                    }
                }
            }else{
                viewModelScope.launch {
                    val title = inputTitle.value
                    val text = inputText.value

                    val newNote = Note(null, title, text, Date())
                    val id = repository.insert(newNote)
                    if(ifInserted(id)){
                        clearFieldValues()
                        _eventAction.value = Event.Insert("Note has been created!")
                    }
                }
            }
        }

    }

    private fun checkFieldValues(): Boolean{
        if((inputTitle.value == "" || inputTitle.value == null) && (inputText.value == "" || inputText.value == null)){
            _eventMessage.value = EventMessage.EmptyFields("Both fields can't be empty at the same time!")
            return false
        }else{
            return true
        }
    }

    private fun ifInserted(id: Long): Boolean{
        return id > -1
    }

    private fun clearFieldValues(){
        inputTitle.value = null
        inputText.value = null
    }
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

}