package com.rober.simpletodonotes.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.rober.simpletodonotes.R
import com.rober.simpletodonotes.model.Note
import com.rober.simpletodonotes.ui.base.BaseActivity

import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel>() {

    override val mViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        insertNote()
        initNotes()
    }

    private fun initNotes(){
        mViewModel.notes.observe(this@MainActivity, Observer {list ->
            Log.i("MainActivty", list.toString())
        })
    }

    private fun insertNote(){
        mViewModel.insertNote(Note(0, "Hola", Date()))
    }

}