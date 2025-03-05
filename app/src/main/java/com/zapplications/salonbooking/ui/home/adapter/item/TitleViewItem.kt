package com.zapplications.salonbooking.ui.home.adapter.item

import com.zapplications.salonbooking.core.adapter.Item

data class TitleViewItem(
    val title: String,
    val actionText: String? = null,
    val actionClickHandler: (() -> Unit)? = null,
) : Item() {
    override val type: Int get() = 3
    override var marginBottomPx: Int = 24

    override fun areContentsTheSame(new: Item): Boolean {
        return this == (new as? TitleViewItem)
    }

    override fun areItemsTheSame(new: Item): Boolean {
        val oldItem = this
        val newItem = new as? TitleViewItem ?: return false
        return oldItem.title == newItem.title && oldItem.actionText == newItem.actionText
    }
}
