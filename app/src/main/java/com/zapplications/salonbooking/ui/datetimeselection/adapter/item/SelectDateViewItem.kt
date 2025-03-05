package com.zapplications.salonbooking.ui.datetimeselection.adapter.item

import com.zapplications.salonbooking.domain.model.datetime.DateUiModel
import com.zapplications.salonbooking.core.adapter.Item

data class SelectDateViewItem(
    val dateUiModel: List<DateUiModel>,
    val selectedDate: DateUiModel? = null,
    val clickHandler: (date: DateUiModel, viewItem: SelectDateViewItem, Int) -> Unit = { _, _, _ -> },
    val moreClickHandler: () -> Unit = {},
) : Item() {
    override val type: Int get() = 1

    override var marginBottomPx: Int = 32

    override fun areItemsTheSame(new: Item): Boolean {
        val oldItem = this
        val newItem = new as? SelectDateViewItem ?: return false
        return oldItem.dateUiModel == newItem.dateUiModel
                && oldItem.selectedDate == newItem.selectedDate
    }

    override fun areContentsTheSame(new: Item): Boolean = this == new
}
