package com.example.alodokter_rakamin_android_kelompok1.view.booking.confirm

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CalendarItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view) // item position

        if (position != 0)
            outRect.left = space

        outRect.right = space
        outRect.bottom = space
        outRect.top = space
    }
}