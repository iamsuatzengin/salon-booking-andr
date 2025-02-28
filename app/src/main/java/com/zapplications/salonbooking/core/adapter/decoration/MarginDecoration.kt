package com.zapplications.salonbooking.core.adapter.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zapplications.salonbooking.core.extensions.ZERO
import com.zapplications.salonbooking.core.extensions.dpToPx
import com.zapplications.salonbooking.core.extensions.orZero

class MarginDecoration(
    private val excludeLastItem: Boolean = false,
    private val marginStartPx: Int = ZERO,
    private val marginEndPx: Int = ZERO,
    private val marginTopPx: Int = ZERO,
    private val marginBottomPx: Int = ZERO,
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) return
        val itemCount = parent.adapter?.itemCount.orZero()

        outRect.left = marginStartPx.orZero().dpToPx(view.context)
        outRect.right = marginEndPx.orZero().dpToPx(view.context)
        outRect.top = marginTopPx.orZero().dpToPx(view.context)
        outRect.bottom =
            if (position == itemCount - 1 && excludeLastItem)
                ZERO
            else marginBottomPx.orZero().dpToPx(view.context)
    }
}
