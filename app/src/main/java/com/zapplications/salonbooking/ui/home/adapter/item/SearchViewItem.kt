package com.zapplications.salonbooking.ui.home.adapter.item

data class SearchViewItem(
    val hint: String
)  : Item() {
    override val type: Int get() = 1
    override val marginBottomPx: Int get() = 24

    override fun areContentsTheSame(old: Item, new: Item): Boolean {
        return old == new
    }

    override fun areItemsTheSame(old: Item, new: Item): Boolean {
        val oldItem = old as SearchViewItem
        val newItem = new as SearchViewItem
        return oldItem.hint == newItem.hint
    }
}
