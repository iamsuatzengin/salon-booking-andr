package com.zapplications.salonbooking.core.adapter.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zapplications.salonbooking.core.adapter.BaseItemAdapter
import com.zapplications.salonbooking.core.extensions.ZERO
import com.zapplications.salonbooking.core.extensions.dpToPx
import com.zapplications.salonbooking.core.extensions.orZero

class MultiTypeMarginDecoration(
    private val excludeLastItem: Boolean = false,
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
        val adapter = (parent.adapter as? BaseItemAdapter) ?: return
        val item = adapter.currentList.getOrNull(position)

        outRect.left = item?.marginStartPx.orZero().dpToPx(view.context)
        outRect.right = item?.marginEndPx.orZero().dpToPx(view.context)
        outRect.top = item?.marginTopPx.orZero().dpToPx(view.context)
        outRect.bottom =
            if (position == itemCount - 1 && excludeLastItem)
                ZERO
            else item?.marginBottomPx.orZero().dpToPx(view.context)
    }
}
