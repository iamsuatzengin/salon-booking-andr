package com.zapplications.salonbooking.ui.home.adapter.item

import com.zapplications.salonbooking.core.adapter.Item

data class TitleViewItem(
    val title: String,
    val actionText: String? = null,
    val actionClickHandler: (() -> Unit)? = null,
) : Item() {
    override val type: Int get() = 3
    override var marginBottomPx: Int = 24

    override fun areContentsTheSame(old: Item, new: Item): Boolean {
        return old == new
    }

    override fun areItemsTheSame(old: Item, new: Item): Boolean {
        val oldItem = old as TitleViewItem
        val newItem = new as TitleViewItem
        return oldItem.title == newItem.title && oldItem.actionText == newItem.actionText
    }
}
