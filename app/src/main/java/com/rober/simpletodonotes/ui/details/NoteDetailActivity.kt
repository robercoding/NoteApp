package com.rober.simpletodonotes.ui.details

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.rober.simpletodonotes.R
import com.rober.simpletodonotes.databinding.ActivityNoteDetailBinding
import com.rober.simpletodonotes.model.Note
import com.rober.simpletodonotes.ui.base.BaseActivity
import com.rober.simpletodonotes.ui.main.MainActivity
import com.rober.simpletodonotes.util.Event
import com.rober.simpletodonotes.util.EventMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_note_detail.*

@AndroidEntryPoint
class NoteDetailActivity : BaseActivity<NoteDetailViewModel, ActivityNoteDetailBinding>() {

    override val mViewModel: NoteDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)

        mViewBinding.vm = mViewModel
        mViewBinding.lifecycleOwner = this

        setupAppBar()

        val item: Note? = intent.getParcelableExtra(NOTE_ITEM)
        if(item==null) setViewNewNote() else setViewEditNote(item)


        mViewModel.eventMessage.observe(this, Observer {eventMessage ->
            when(eventMessage) {
                is EventMessage.EmptyFields -> {
                    Toast.makeText(this@NoteDetailActivity, eventMessage.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

        mViewModel.eventAction.observe(this, Observer {eventAction ->
            when(eventAction){
                is Event.Update -> {
                    Toast.makeText(this@NoteDetailActivity, eventAction.message, Toast.LENGTH_SHORT).show()
                    goToMainActivity()
                }

                is Event.Insert -> {
                    Toast.makeText(this@NoteDetailActivity, eventAction.message, Toast.LENGTH_SHORT).show()
                    goToMainActivity()
                }
            }
        })
    }

    private fun setViewEditNote(item: Note) {
        mViewModel.initUpdate(item)
    }

    private fun setViewNewNote() {
        mViewModel.initCreate()
    }

    private fun setupAppBar(){
        detailToolbar.setNavigationOnClickListener {
            val intent = Intent(this@NoteDetailActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        detailToolbar.setOnMenuItemClickListener { menuItem->
            when(menuItem.itemId){
                R.id.save -> {
                    mViewModel.saveNote()
                    true
                }
                else -> {
                    Log.i(TAG, "Nothing happens when you try to save")
                    true
                }
            }
        }
        //set save and more in toolbar
    }

    private fun goToMainActivity(){
        val intent = Intent(this@NoteDetailActivity, MainActivity::class.java)
        startActivity(intent)
    }

    override fun getViewBinding(): ActivityNoteDetailBinding = ActivityNoteDetailBinding.inflate(layoutInflater)

    companion object{
        const val NOTE_ITEM = "noteItem"
    }
}