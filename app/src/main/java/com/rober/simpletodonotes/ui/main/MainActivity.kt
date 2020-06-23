package com.rober.simpletodonotes.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.rober.simpletodonotes.databinding.ActivityMainBinding
import com.rober.simpletodonotes.model.Note
import com.rober.simpletodonotes.ui.base.BaseActivity
import com.rober.simpletodonotes.ui.main.adapter.NoteRecyclerAdapter
import com.rober.simpletodonotes.ui.details.NoteDetailActivity

import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(),
NoteRecyclerAdapter.OnItemClickListener{

    override val mViewModel: MainViewModel by viewModels()
    //private val binding: ActivityMainBinding by binding(R.layout.activity_main)
    private val mAdapter: NoteRecyclerAdapter by lazy { NoteRecyclerAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        insertNote()
        initNotes()

    }

    private fun initNotes(){
        mViewBinding.noteRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = mAdapter
        }

        mViewModel.notes.observe(this@MainActivity, Observer {list ->
            mAdapter.addNoteList(list)
            Log.i("MainActivty", list.toString())
        })
    }

    private fun insertNote(){
        mViewModel.insertNote(Note(0,"Titulo0", "Hola0", Date()))
        mViewModel.insertNote(Note(1,"Titulo1", "Hola1", Date()))

    }

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun onItemClickListener(note: Note) {
        val intent = Intent(this, NoteDetailActivity::class.java)

        intent.putExtra(NoteDetailActivity.NOTE_ID, note.id)
        startActivity(intent)
    }

}