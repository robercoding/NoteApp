package com.rober.simpletodonotes.ui.main.state

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rober.simpletodonotes.model.Note
import com.rober.simpletodonotes.ui.main.state.NoteToolbarState.DefaultState

const val TAG = "NoteInteractionManager"
class NoteInteractionManager {

    private var _toolbarState: MutableLiveData<NoteToolbarState>
            = MutableLiveData(DefaultState())

    private var _selectedNotes: MutableLiveData<ArrayList<Note>> = MutableLiveData()

    val selectedNotes: LiveData<ArrayList<Note>>
            get() = _selectedNotes

    val toolbarState : LiveData<NoteToolbarState>
        get() = _toolbarState

    fun setToolbarState(state: NoteToolbarState){
        _toolbarState.value = state
    }

    fun isMultiSelectionActivated(): Boolean{
        return toolbarState.value.toString() == NoteToolbarState.MultiSelectionState().toString()
    }

    fun addOrRemoveNoteFromSelectedList(note:Note) {
        var list = _selectedNotes.value
        if(list == null){
            list = ArrayList()
        }

        if(list.contains(note)){
            list.remove(note)
        }else{
            list.add(note)
        }

        _selectedNotes.value = list
        Log.i(TAG, "look list: ${_selectedNotes.value.toString()}")
        checkListValue()
    }

    fun getSelectedNotes():ArrayList<Note> = _selectedNotes.value?: ArrayList()

    fun clearSelectedNotes(){
        _selectedNotes.value = null
        setDefaultState()
    }

    private fun checkListValue(){
        val list = _selectedNotes.value
        if(list == null || list.size == 0){
            setDefaultState()
        }
    }

    private fun setDefaultState(){
        _toolbarState.value = DefaultState()
    }
}