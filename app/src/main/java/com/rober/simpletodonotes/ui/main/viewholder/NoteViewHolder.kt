package com.rober.simpletodonotes.ui.main.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.rober.simpletodonotes.databinding.ItemNoteBinding
import com.rober.simpletodonotes.model.Note
import com.rober.simpletodonotes.ui.main.adapter.NoteRecyclerAdapter

class NoteViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root){

    fun bind(item:Note, onItemClickListener: NoteRecyclerAdapter.OnItemClickListener ?= null){
        binding.apply {
            note = item
            executePendingBindings()

            onItemClickListener?.let {listener->
                binding.root.setOnClickListener {
                    listener.onItemClickListener(item)
                }
            }
        }
    }
}