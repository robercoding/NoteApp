package com.rober.simpletodonotes.ui.main.viewholder

import android.graphics.Color
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.rober.simpletodonotes.R
import com.rober.simpletodonotes.databinding.ItemNoteBinding
import com.rober.simpletodonotes.model.Note
import com.rober.simpletodonotes.ui.common.changeStrokeColor
import com.rober.simpletodonotes.ui.common.changeStrokeWidth
import com.rober.simpletodonotes.ui.main.adapter.NoteRecyclerAdapter

const val TAG = "NoteViewHolder"
class NoteViewHolder(
    val binding: ItemNoteBinding,
    private val interaction: NoteRecyclerAdapter.Interaction?,
    private val viewLifecycleOwner: LifecycleOwner,
    private val selectedNotes: LiveData<ArrayList<Note>>
) : RecyclerView.ViewHolder(binding.root){

    fun bind(item:Note){
        binding.apply {
            note = item
            //Check for title value and set visible or not
            if(item.title == null || item.title == "") title.visibility = View.GONE else title.visibility = View.VISIBLE

            //Check for text value and set visible or not
            if(item.text == null || item.text == "") text.visibility = View.GONE else text.visibility = View.VISIBLE

            executePendingBindings()

            interaction?.let {interaction->
                binding.root.setOnClickListener {
                    interaction.onItemClick(item)
                }

                binding.root.setOnLongClickListener {
                    interaction.activateMultiSelectItem()
                    interaction.onItemClick(item)
                    true
                }
            }

            selectedNotes.observe(viewLifecycleOwner, Observer {notes ->
                if(notes!=null){
                    if(notes.contains(item)){
                        itemCardView.apply{
                            changeStrokeColor(R.color.strokeCardSelected)
                            changeStrokeWidth(5)
                        }
                    }else{
                        itemCardView.apply {
                            changeStrokeColor(R.color.strokeCard)
                            changeStrokeWidth(2)
                        }
                    }
                }else{
                    itemCardView.apply {
                        changeStrokeColor(R.color.strokeCard)
                        changeStrokeWidth(2)
                    }
                }
            })
        }
    }
}