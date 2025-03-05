package com.zapplications.salonbooking.ui.datetimeselection.adapter.item

import com.zapplications.salonbooking.domain.model.datetime.TimeUiModel
import com.zapplications.salonbooking.core.adapter.Item

data class SelectTimeViewItem(
    val timeUiModel: TimeUiModel,
    val discount: Int = 0,
    val isSelected: Boolean = false,
    val isAvailable: Boolean = true,
    val clickHandler: (SelectTimeViewItem, Int) -> Unit = { _, _ -> },
) : Item() {
    override val type: Int get() = 2
    override var marginBottomPx: Int = 16

    override fun areItemsTheSame(new: Item): Boolean {
        val oldItem = this
        val newItem = new as? SelectTimeViewItem ?: return false
        return oldItem.timeUiModel == newItem.timeUiModel
                && oldItem.timeUiModel.time == newItem.timeUiModel.time
                && oldItem.isSelected == newItem.isSelected
                && oldItem.isAvailable == newItem.isAvailable
    }

    override fun areContentsTheSame(new: Item): Boolean {
        val oldItem = this
        val newItem = new as? SelectTimeViewItem ?: return false
        return oldItem == newItem
    }
}
