package com.rober.simpletodonotes.ui.details

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.rober.simpletodonotes.databinding.ActivityNoteDetailBinding
import com.rober.simpletodonotes.ui.base.BaseActivity
import java.lang.IllegalArgumentException

class NoteDetailActivity : BaseActivity<NoteDetailViewModel, ActivityNoteDetailBinding>() {

    override val mViewModel: NoteDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)

        val postId = intent.extras?.getLong(NOTE_ID)
            ?: throw IllegalArgumentException("note id must be non null")

        Log.i("NoteDetailActivity", "Id: $postId")
    }

    override fun getViewBinding(): ActivityNoteDetailBinding = ActivityNoteDetailBinding.inflate(layoutInflater)

    companion object{
        const val NOTE_ID = "noteId"
    }
}