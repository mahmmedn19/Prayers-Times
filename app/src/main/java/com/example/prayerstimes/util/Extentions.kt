package com.example.prayerstimes.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// Extension function for RecyclerView to scroll to a specific position
fun RecyclerView.scrollToPositionSmooth(position: Int) {
    smoothScrollToPosition(position)
    post {
        val layoutManager = layoutManager as? LinearLayoutManager
        layoutManager?.findFirstCompletelyVisibleItemPosition()?.let { newVisiblePosition ->
            if (newVisiblePosition != RecyclerView.NO_POSITION) {
                layoutManager.findFirstCompletelyVisibleItemPosition()
            }
        }
    }
}

fun LinearLayoutManager.getCurrentVisiblePosition(): Int {
    return findFirstCompletelyVisibleItemPosition()
}

