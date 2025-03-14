package com.zapplications.salonbooking.ui.stylistlist.adapter.item

import com.zapplications.salonbooking.core.adapter.Item
import com.zapplications.salonbooking.domain.model.StylistUiModel

data class StylistViewItem(
    val stylistUiModel: StylistUiModel,
    val isSelected: Boolean = false,
    val onClick: (StylistViewItem, Int) -> Unit
): Item() {
    override val type: Int get() = StylistViewType.STYLIST.ordinal

    override fun areItemsTheSame(new: Item): Boolean {
        val newItem = new as? StylistViewItem ?: return false
        return this.stylistUiModel.id == newItem.stylistUiModel.id
                && this.stylistUiModel.fullName == newItem.stylistUiModel.fullName
                && this.isSelected == newItem.isSelected
    }

    override fun areContentsTheSame(new: Item): Boolean = this == new
}
