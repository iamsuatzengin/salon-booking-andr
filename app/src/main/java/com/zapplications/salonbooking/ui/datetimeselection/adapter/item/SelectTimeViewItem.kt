package com.zapplications.salonbooking.ui.datetimeselection.adapter.item

import com.zapplications.salonbooking.domain.model.datetime.TimeUiModel
import com.zapplications.salonbooking.core.adapter.Item

class SelectTimeViewItem(
    val timeUiModel: TimeUiModel,
    val discount: Int = 0,
    var isSelected: Boolean = false,
    val clickHandler: (SelectTimeViewItem, Int) -> Unit = { _, _ -> },
) : Item() {
    override val type: Int get() = 2
    override var marginBottomPx: Int = 16

    override fun areItemsTheSame(old: Item, new: Item): Boolean {
        val oldItem = old as SelectTimeViewItem
        val newItem = new as SelectTimeViewItem
        return oldItem.timeUiModel == newItem.timeUiModel
                && oldItem.timeUiModel.time == newItem.timeUiModel.time
    }

    override fun areContentsTheSame(old: Item, new: Item): Boolean = old == new
}
