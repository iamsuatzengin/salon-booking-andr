package com.zapplications.salonbooking.ui.stylistlist.adapter.item

import com.zapplications.salonbooking.core.adapter.Item

data class AnyStylistViewItem(
    val isSelected: Boolean = false,
    val onClick: (AnyStylistViewItem, Int) -> Unit
) : Item() {
    override val type: Int get() = StylistViewType.ANY_STYLIST.ordinal

    override fun areItemsTheSame(new: Item): Boolean = this == new
    override fun areContentsTheSame(new: Item): Boolean = this == new
}
