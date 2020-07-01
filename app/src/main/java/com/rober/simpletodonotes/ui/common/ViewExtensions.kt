package com.rober.simpletodonotes.ui.common

import android.R
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// threshold for when contents of collapsing toolbar is hidden
const val COLLAPSING_TOOLBAR_VISIBILITY_THRESHOLD = -75
const val CLICK_THRESHOLD = 150L // a click is considered 150ms or less
const val CLICK_COLOR_CHANGE_TIME = 250L


fun MaterialCardView.changeStrokeColor(newColor: Int){

    setStrokeColor(
        ContextCompat.getColor(context, newColor)
    )
}

fun MaterialCardView.changeStrokeWidth(width: Int){
    strokeWidth = width
}

fun View.changeColor(newColor: Int) {
    setBackgroundColor(
        ContextCompat.getColor(
            context,
            newColor
        )
    )
}













