package com.rober.simpletodonotes.ui.main

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.rober.simpletodonotes.R
import com.rober.simpletodonotes.databinding.ActivityMainBinding
import com.rober.simpletodonotes.model.Note
import com.rober.simpletodonotes.ui.base.BaseActivity
import com.rober.simpletodonotes.ui.main.adapter.NoteRecyclerAdapter
import com.rober.simpletodonotes.ui.details.NoteDetailActivity
import com.rober.simpletodonotes.util.Event

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(),
NoteRecyclerAdapter.OnItemClickListener {

    private lateinit var appSettingsPrefs : SharedPreferences
    private lateinit var sharedPrefsEdit : SharedPreferences.Editor
    private var isNightModeOn: Boolean = false


    override val mViewModel: MainViewModel by viewModels()
    private val mAdapter: NoteRecyclerAdapter by lazy { NoteRecyclerAdapter(this) }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)

        initPreferences()
        setupAppBar()

        initNotes()


        fab.setOnClickListener {
            goToDetailsActivity()
        }

        mViewModel.event.observe(this@MainActivity, Observer {event ->
            when(event) {
                is Event.Delete -> {
                    Snackbar.make(mViewBinding.root, "Note has been deleted", 5000).apply {
                        setAction("Undo"){
                            mViewModel.insertNote(event.data!!)
                        }.show()
                    }
                }
            }
        })

        val simpleItemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val holderPosition = viewHolder.adapterPosition
                val note = mAdapter.getNote(holderPosition)
                mViewModel.deleteNote(note)
            }
        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchHelper)
        itemTouchHelper.apply {
            attachToRecyclerView(note_recyclerView)
        }
    }

    private fun initPreferences(){
        appSettingsPrefs = getSharedPreferences("AppSettingPrefs", 0)
        sharedPrefsEdit = appSettingsPrefs.edit()
        isNightModeOn = appSettingsPrefs.getBoolean("NightMode", false)

        if(isNightModeOn){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun initNotes(){
        mViewBinding.noteRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = mAdapter
        }

        mViewModel.notes.observe(this@MainActivity, Observer {list ->
            mAdapter.addNoteList(list)
            Log.i("MainActivty", list.toString())
        })
    }

    private fun setupAppBar(){
        mViewBinding.mainToolbar.setOnMenuItemClickListener {menuItem->
            when(menuItem.itemId) {
                R.id.bulb -> {
                    if(isNightModeOn){
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        sharedPrefsEdit.putBoolean("NightMode", false)
                        sharedPrefsEdit.apply()
                    }else{
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        sharedPrefsEdit.putBoolean("NightMode", true)
                        sharedPrefsEdit.apply()
                    }
                    true
                }
                else -> true
            }

        }
    }


    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun onItemClickListener(note: Note) {
        val intent = Intent(this, NoteDetailActivity::class.java)
        intent.putExtra(NoteDetailActivity.NOTE_ITEM, note)
        startActivity(intent)
    }

    private fun goToDetailsActivity(){
        val intent = Intent(this, NoteDetailActivity::class.java)
        startActivity(intent)
        Log.i("MainActivity", "Works")
    }

}