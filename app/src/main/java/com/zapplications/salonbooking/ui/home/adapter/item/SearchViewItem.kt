package com.zapplications.salonbooking.ui.home.adapter.item

import com.zapplications.salonbooking.core.adapter.Item

data class SearchViewItem(
    val hint: String
)  : Item() {
    override val type: Int get() = 1
    override var marginBottomPx: Int = 24

    override fun areContentsTheSame(new: Item): Boolean {
        return this == (new as? SearchViewItem)
    }

    override fun areItemsTheSame(new: Item): Boolean {
        val oldItem = this
        val newItem = new as? SearchViewItem ?: return false
        return oldItem.hint == newItem.hint
    }
}
