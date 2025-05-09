package com.zapplications.salonbooking.core.ui.applyinset

import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import androidx.core.view.updateLayoutParams

/**
 * Specifies which sides of a view should have system bar insets applied as margins.
 *
 * @property left Whether to apply the left system bar inset as a left margin.
 * @property top Whether to apply the top system bar inset as a top margin.
 * @property right Whether to apply the right system bar inset as a right margin.
 * @property bottom Whether to apply the bottom system bar inset as a bottom margin.
 * @property isConsumed Whether the insets should be consumed after applying them.
 */
data class InsetSides(
    val left: Boolean = false,
    val top: Boolean = false,
    val right: Boolean = false,
    val bottom: Boolean = false,
    val isConsumed: Boolean = true,
)

/**
 * Applies the system bar insets as padding to the view.
 *
 * This function sets an [ViewCompat.setOnApplyWindowInsetsListener] on the view that will automatically
 * adjust the view's padding whenever the system bar insets change. The insets are applied
 * based on the specified [insetSides].
 *
 * @param insetSides A data class defining which sides of the view should have the system bar
 * insets applied as padding. Also [InsetSides] has a `isConsumed` parameter. Its default value is `true`
 */
fun View.applySystemBarInsetsAsPadding(insetSides: InsetSides) {
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

        view.setPadding(
            if (insetSides.left) systemBars.left else view.paddingLeft,
            if (insetSides.top) systemBars.top else view.paddingTop,
            if (insetSides.right) systemBars.right else view.paddingRight,
            if (insetSides.bottom) systemBars.bottom else view.paddingBottom
        )

        if (insetSides.isConsumed) WindowInsetsCompat.CONSUMED else insets
    }
}

fun View.applySystemBarInsetsAsMargin(insetSides: InsetSides) {
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

        view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            marginStart = if (insetSides.left) systemBars.left else view.marginStart
            topMargin = if (insetSides.top) systemBars.top else view.marginTop
            marginEnd = if (insetSides.right) systemBars.right else view.marginEnd
            bottomMargin = if (insetSides.bottom) systemBars.bottom else view.marginBottom
        }

        if (insetSides.isConsumed) WindowInsetsCompat.CONSUMED else insets
    }
}
