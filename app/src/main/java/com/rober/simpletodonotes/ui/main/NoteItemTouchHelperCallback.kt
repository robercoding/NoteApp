package com.rober.simpletodonotes.ui.main

import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.rober.simpletodonotes.R
import com.rober.simpletodonotes.ui.common.changeStrokeColor
import com.rober.simpletodonotes.ui.common.changeStrokeWidth
import com.rober.simpletodonotes.util.ItemTouchHelperAdapter
import kotlinx.android.synthetic.main.item_note.view.*


class NoteItemTouchHelperCallback(
    private val itemTouchHelper: ItemTouchHelperAdapter
): ItemTouchHelper.Callback() {
    private var isDropped: Boolean = false

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val drag:Int = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        val swipe:Int = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(drag, swipe)
    }

    override fun isLongPressDragEnabled(): Boolean {
        return false
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        isDropped = true
        Log.i(TAG, "Clear View")
        Log.i(TAG, "ID onClear = ${viewHolder.itemView.id}")
        viewHolder.itemView.itemCardView.apply {
            changeStrokeColor(R.color.fuchsia)
            changeStrokeWidth(2)
        }
        Toast.makeText(viewHolder.itemView.context, "Clear view", Toast.LENGTH_SHORT).show()
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        Log.i(TAG, "ACTION STATE= $actionState")
        if(actionState == ItemTouchHelper.ACTION_STATE_DRAG){
            viewHolder?.itemView?.itemCardView?.apply {
                isDropped = false
                Log.i(TAG, "On Selected")
                changeStrokeColor(R.color.strokeCardSelected)
                changeStrokeWidth(5)
            }
        }
        if(actionState == ItemTouchHelper.ACTION_STATE_IDLE){
            isDropped = true
            /*viewHolder?.itemView?.itemCardView?.apply {
                changeStrokeColor(R.color.strokeCard)
                changeStrokeWidth(2)
            }*/
            Log.i(TAG, "Dropped true")
        }
        //if(actionState == ItemTouchHelper.)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {

        /*viewHolder.itemView.itemCardView.apply {
            Log.i(TAG, "ON MOVE Is card dragged= $isDragged")
            changeStrokeColor(R.color.yellow)
            changeStrokeWidth(5)
            Log.i(TAG, "Change UI card")
        }*/

        Log.i(TAG, "ID onMove = ${viewHolder.itemView.id}")
        itemTouchHelper.onItemMove(viewHolder.adapterPosition, target.adapterPosition, viewHolder, isDropped)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        itemTouchHelper.onItemSwiped(viewHolder.adapterPosition)
    }
}