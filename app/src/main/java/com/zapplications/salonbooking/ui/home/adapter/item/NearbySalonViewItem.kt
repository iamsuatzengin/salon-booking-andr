package com.zapplications.salonbooking.ui.home.adapter.item

import com.zapplications.salonbooking.core.adapter.Item
import com.zapplications.salonbooking.domain.model.SalonUiModel

data class NearbySalonViewItem(
    val salonUiModel: SalonUiModel,
    val clickHandler: (salonId: String) -> Unit = {},
) : Item() {
    override val type: Int get() = 5

    override var marginBottomPx: Int = 16

    override fun areContentsTheSame(old: Item, new: Item): Boolean {
        return old == new
    }

    override fun areItemsTheSame(old: Item, new: Item): Boolean {
        val oldItem = old as NearbySalonViewItem
        val newItem = new as NearbySalonViewItem
        return oldItem.salonUiModel == newItem.salonUiModel && oldItem.salonUiModel.id == newItem.salonUiModel.id
    }
}
