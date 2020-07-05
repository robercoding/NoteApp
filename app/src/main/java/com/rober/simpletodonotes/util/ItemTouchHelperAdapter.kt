package com.rober.simpletodonotes.util

import androidx.recyclerview.widget.RecyclerView

interface ItemTouchHelperAdapter {

    fun onItemMove(fromPosition: Int, toPosition: Int, viewHolder: RecyclerView.ViewHolder, isDropped: Boolean)

    fun onItemSwiped(position: Int)
}