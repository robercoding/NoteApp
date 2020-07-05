package com.rober.simpletodonotes.util

import androidx.recyclerview.widget.RecyclerView

interface ItemTouchHelperAdapter {

    fun onItemMove(fromPosition: Int, toPosition: Int, viewHolder: RecyclerView.ViewHolder)

    fun onItemSwiped(position: Int)
}