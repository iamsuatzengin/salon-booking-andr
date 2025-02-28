package com.zapplications.salonbooking.ui.home.adapter.item

import com.zapplications.salonbooking.core.adapter.Item

data class TopViewItem(
    val title: String,
    val locationString: String,
    val clickHandler: (() -> Unit)? = null
) : Item() {
    override val type: Int = 0
    override var marginTopPx: Int = 24
    override var marginBottomPx: Int = 32

    override fun areContentsTheSame(old: Item, new: Item): Boolean {
        return old == new
    }

    override fun areItemsTheSame(old: Item, new: Item): Boolean {
        val oldItem = old as TopViewItem
        val newItem = new as TopViewItem
        return oldItem.title == newItem.title && oldItem.locationString == newItem.locationString
    }
}
