package com.rober.simpletodonotes.ui.main.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.rober.simpletodonotes.R
import com.rober.simpletodonotes.databinding.ItemNoteBinding
import com.rober.simpletodonotes.model.Note
import com.rober.simpletodonotes.ui.details.TAG
import com.rober.simpletodonotes.ui.main.viewholder.NoteViewHolder

class NoteRecyclerAdapter(
    private val interaction: Interaction?,
    private val lifecycleOwner: LifecycleOwner,
    private val selectedNotes: LiveData<ArrayList<Note>>
) : RecyclerView.Adapter<NoteViewHolder>() {

    private val items: MutableList<Note> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemNoteBinding>(inflater, R.layout.item_note, parent, false)
        return NoteViewHolder(binding, interaction, lifecycleOwner, selectedNotes)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

        val note = items[position]
        holder.bind(note)
    }

    fun addNoteList(notes: List<Note>){
        items.clear()
        items.addAll(notes)
        notifyDataSetChanged()
    }

    fun getNote(position: Int): Note{
        return items[position]

    }

    override fun getItemCount(): Int = items.size

    interface Interaction{
        fun onItemClick(note:Note)

        fun activateMultiSelectItem()

        fun isMultiSelectionStateActivated() : Boolean
    }

}