package com.zapplications.salonbooking.ui.datetimeselection.adapter.item

import com.zapplications.salonbooking.domain.model.datetime.DateUiModel
import com.zapplications.salonbooking.core.adapter.Item

class SelectDateViewItem(
    val dateUiModel: List<DateUiModel>,
    var selectedDate: DateUiModel? = null,
    val clickHandler: (date: DateUiModel, viewItem: SelectDateViewItem, Int) -> Unit = { _, _, _ -> },
    val moreClickHandler: () -> Unit = {},
) : Item() {
    override val type: Int get() = 1

    override var marginBottomPx: Int = 32

    override fun areItemsTheSame(old: Item, new: Item): Boolean {
        val oldItem = old as SelectDateViewItem
        val newItem = new as SelectDateViewItem
        return oldItem.dateUiModel == newItem.dateUiModel
    }

    override fun areContentsTheSame(old: Item, new: Item): Boolean = old == new
}
