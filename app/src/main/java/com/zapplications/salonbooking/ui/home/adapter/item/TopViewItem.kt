package com.zapplications.salonbooking.ui.home.adapter.item

data class TopViewItem(
    val title: String,
    val locationString: String,
    val clickHandler: (() -> Unit)? = null
) : Item() {
    override val type: Int = 0
    override val marginTopPx: Int get() = 24
    override val marginBottomPx: Int = 32

    override fun areContentsTheSame(old: Item, new: Item): Boolean {
        return old == new
    }

    override fun areItemsTheSame(old: Item, new: Item): Boolean {
        val oldItem = old as TopViewItem
        val newItem = new as TopViewItem
        return oldItem.title == newItem.title && oldItem.locationString == newItem.locationString
    }
}
