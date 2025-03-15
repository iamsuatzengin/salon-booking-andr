package com.zapplications.salonbooking.core.adapter.commonview.emptystate

import androidx.annotation.DrawableRes
import com.zapplications.salonbooking.core.adapter.Item

data class EmptyStateViewItem(
    @DrawableRes val imageRes: Int,
    val title: String,
    val body: String
) : Item() {
    override val type: Int get() = 999

    override fun areItemsTheSame(new: Item): Boolean {
        val oldItem = this
        val newItem = new as? EmptyStateViewItem ?: return false
        return oldItem.imageRes == newItem.imageRes
                && oldItem.title == newItem.title
                && oldItem.body == newItem.body
    }

    override fun areContentsTheSame(new: Item): Boolean = this == (new as? EmptyStateViewItem)
}
