package com.rober.simpletodonotes.ui.main

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.rober.simpletodonotes.R
import com.rober.simpletodonotes.databinding.ActivityMainBinding
import com.rober.simpletodonotes.model.Note
import com.rober.simpletodonotes.ui.base.BaseActivity
import com.rober.simpletodonotes.ui.main.adapter.NoteRecyclerAdapter
import com.rober.simpletodonotes.ui.details.NoteDetailActivity
import com.rober.simpletodonotes.ui.main.state.NoteToolbarState
import com.rober.simpletodonotes.util.Event

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(),
NoteRecyclerAdapter.Interaction {

    private lateinit var appSettingsPrefs : SharedPreferences
    private lateinit var sharedPrefsEdit : SharedPreferences.Editor
    private var isNightModeOn: Boolean = false

    override val mViewModel: MainViewModel by viewModels()
    private var mAdapter: NoteRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)

        initPreferences()
        setupAppBar()
        initNotes()
        subscribeObservers()


        fab.setOnClickListener {
            goToDetailsActivity()
        }



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
                val note = mAdapter!!.getNote(holderPosition)
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
            mAdapter = NoteRecyclerAdapter(this@MainActivity, this@MainActivity, mViewModel.noteInteractionManager.selectedNotes)
            adapter = mAdapter
        }
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

                R.id.delete -> {
                    val itemsCount = mViewModel.noteInteractionManager.getSelectedNotes().size

                    MaterialAlertDialogBuilder(mViewBinding.root.context)
                        .setTitle("Deleting items")
                        .setMessage("Are you sure you want to delete $itemsCount items?")
                        .setPositiveButton("Yes"){dialog, which ->
                            mViewModel.deleteSelectedNotes()
                        }
                        .setNegativeButton("No"){dialog, which ->  }
                        .show()
                    true
                }
                R.id.clear -> {
                    mViewModel.noteInteractionManager.clearSelectedNotes()
                    disableAnyMenuState()
                    enableDefaultState()
                    true
                }
                else -> true
            }

        }
    }

    private fun subscribeObservers(){
        mViewModel.toolbarState.observe(this@MainActivity, Observer {state ->
            when(state){
                is NoteToolbarState.MultiSelectionState -> {
                    disableAnyMenuState()
                    enableMultiselectionState()
                }

                is NoteToolbarState.DefaultState -> {
                    disableAnyMenuState()
                    enableDefaultState()
                }

            }
        })

        mViewModel.eventNotes.observe(this@MainActivity, Observer {event ->
            when(event) {
                is Event.Delete -> {
                    Snackbar.make(mViewBinding.root, "${event.message}", 5000).apply {
                        setAction("Undo"){
                            val notes = event.data!!
                            for (note in notes){
                                mViewModel.insertNote(note)
                            }
                        }.show()
                    }
                }
            }
        })


        mViewModel.notes.observe(this@MainActivity, Observer {list ->
            mAdapter!!.addNoteList(list)
            Log.i("MainActivty", list.toString())
        })

    }

    override fun isMultiSelectionStateActivated() = mViewModel.isMultiSelectionActivated()

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun onItemClick(note: Note) {
        if(isMultiSelectionStateActivated()){
            mViewModel.addOrRemoveNoteFromSelectedList(note)
        }else{
            val intent = Intent(this, NoteDetailActivity::class.java)
            intent.putExtra(NoteDetailActivity.NOTE_ITEM, note)
            startActivity(intent)
        }
    }

    override fun activateMultiSelectItem() {
        mViewModel.setToolbarState(NoteToolbarState.MultiSelectionState())
    }

    private fun goToDetailsActivity(){
        val intent = Intent(this, NoteDetailActivity::class.java)
        startActivity(intent)
        Log.i("MainActivity", "Works")
    }

    private fun disableAnyMenuState(){
        mViewBinding.mainToolbar.menu.clear()
    }

    private fun enableMultiselectionState(){
        mViewBinding.mainToolbar.inflateMenu(R.menu.main_bar_multiselection_state)
    }
    private fun enableDefaultState(){
        mViewBinding.mainToolbar.inflateMenu(R.menu.main_bar_default_state)
    }
}