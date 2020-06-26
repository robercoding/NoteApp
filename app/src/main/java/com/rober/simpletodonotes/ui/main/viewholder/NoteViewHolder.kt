package com.rober.simpletodonotes.ui.main.viewholder

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.rober.simpletodonotes.databinding.ItemNoteBinding
import com.rober.simpletodonotes.model.Note
import com.rober.simpletodonotes.ui.main.adapter.NoteRecyclerAdapter

const val TAG = "NoteViewHolder"
class NoteViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root){

    fun bind(item:Note, onItemClickListener: NoteRecyclerAdapter.OnItemClickListener ?= null){
        binding.apply {
            note = item
            //Check for title value and set visible or not
            if(item.title == null || item.title == "") title.visibility = View.GONE else title.visibility = View.VISIBLE

            //Check for text value and set visible or not
            if(item.text == null || item.text == "") text.visibility = View.GONE else text.visibility = View.VISIBLE

            executePendingBindings()
            onItemClickListener?.let {listener->
                binding.root.setOnClickListener {
                    listener.onItemClickListener(item)
                }
            }
        }
    }
}