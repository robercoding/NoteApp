package com.rober.simpletodonotes.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.rober.simpletodonotes.R
import com.rober.simpletodonotes.databinding.ItemNoteBinding
import com.rober.simpletodonotes.model.Note
import com.rober.simpletodonotes.ui.main.viewholder.NoteViewHolder

class NoteRecyclerAdapter(private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<NoteViewHolder>() {

    private val items: MutableList<Note> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemNoteBinding>(inflater, R.layout.item_note, parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

        val note = items[position]
        holder.bind(note, onItemClickListener)
    }

    fun addNoteList(notes: List<Note>){
        items.clear()
        items.addAll(notes)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    interface OnItemClickListener{
        fun onItemClickListener(note:Note)
    }

}