package com.zapplications.salonbooking.ui.home.adapter.item

import com.zapplications.salonbooking.core.adapter.Item
import com.zapplications.salonbooking.domain.model.ServiceCategoryUiModel

data class ServiceCategoryViewItem(
    val categories: List<ServiceCategoryUiModel?>,
    val onCategoryClick: (ServiceCategoryUiModel) -> Unit,
) : Item() {
    override val type: Int get() = 4
    override var marginBottomPx: Int = 32

    override fun areContentsTheSame(new: Item, ): Boolean = this == (new as? ServiceCategoryViewItem)

    override fun areItemsTheSame(new: Item, ): Boolean {
        val oldItem = this
        val newItem = new as? ServiceCategoryViewItem ?: return false
        return oldItem.categories == newItem.categories && oldItem.categories.size == newItem.categories.size
    }
}
