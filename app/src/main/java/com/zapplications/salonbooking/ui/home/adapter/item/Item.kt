package com.zapplications.salonbooking.ui.home.adapter.item

abstract class Item {
    abstract val type: Int

    open val marginTopPx: Int = 0
    open val marginBottomPx: Int = 0
    open val marginStartPx: Int = 0
    open val marginEndPx: Int = 0

    abstract fun areItemsTheSame(old: Item, new: Item): Boolean
    abstract fun areContentsTheSame(old: Item, new: Item): Boolean
}
