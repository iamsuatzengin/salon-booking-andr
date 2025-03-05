package com.zapplications.salonbooking.core.adapter

abstract class Item {
    abstract val type: Int

    open var marginTopPx: Int = 0
    open var marginBottomPx: Int = 0
    open var marginStartPx: Int = 0
    open var marginEndPx: Int = 0

    abstract fun areItemsTheSame(new: Item): Boolean
    abstract fun areContentsTheSame(new: Item): Boolean
}
