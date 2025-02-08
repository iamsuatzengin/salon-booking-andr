package com.zapplications.salonbooking.ui.home.adapter.item

import com.zapplications.salonbooking.domain.model.ServiceCategoryUiModel

data class ServiceCategoryViewItem(
    val categories: List<ServiceCategoryUiModel>,
) : Item() {
    override val type: Int get() = 4
    override fun areContentsTheSame(
        old: Item,
        new: Item,
    ): Boolean = old == new


    override fun areItemsTheSame(
        old: Item,
        new: Item,
    ): Boolean {
        val oldItem = old as ServiceCategoryViewItem
        val newItem = new as ServiceCategoryViewItem
        return oldItem.categories == newItem.categories && oldItem.categories.size == newItem.categories.size
    }
}
