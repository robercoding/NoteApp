package com.rober.simpletodonotes.ui.main

import android.util.Log
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

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val drag:Int = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        val swipe:Int = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(drag, swipe)
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        viewHolder.itemView.itemCardView?.apply {
            changeStrokeColor(R.color.strokeCard)
            changeStrokeWidth(2)
        }
        Log.i(TAG, "Color!= ${viewHolder.itemView.itemCardView.strokeColor}")
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        Log.i(TAG, "ACTION STATE= $actionState")
        if(actionState == ItemTouchHelper.ACTION_STATE_DRAG){
            viewHolder?.itemView?.itemCardView?.apply {
                changeStrokeColor(R.color.strokeCardSelected)
                changeStrokeWidth(5)
            }
        }
        if(actionState == ItemTouchHelper.ACTION_STATE_IDLE){
            viewHolder?.itemView?.itemCardView?.apply {
                changeStrokeColor(R.color.strokeCardSelected)
                changeStrokeWidth(5)
            }
        }
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        itemTouchHelper.onItemMove(viewHolder.adapterPosition, target.adapterPosition, viewHolder)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        itemTouchHelper.onItemSwiped(viewHolder.adapterPosition)
    }
}