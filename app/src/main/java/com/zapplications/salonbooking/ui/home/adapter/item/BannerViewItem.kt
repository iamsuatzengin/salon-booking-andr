package com.zapplications.salonbooking.ui.home.adapter.item

import com.zapplications.salonbooking.core.adapter.Item
import com.zapplications.salonbooking.domain.model.BannerUiModel

data class BannerViewItem(
    val bannerUiModel: BannerUiModel,
    val bookNowClick: (() -> Unit)? = null
) : Item() {
    override val type: Int get() = 2
    override var marginBottomPx: Int = 32

    override fun areContentsTheSame(old: Item, new: Item): Boolean {
        return old == new
    }

    override fun areItemsTheSame(old: Item, new: Item): Boolean {
        val oldItem = old as BannerViewItem
        val newItem = new as BannerViewItem
        return oldItem.bannerUiModel == newItem.bannerUiModel && oldItem.bannerUiModel.id == newItem.bannerUiModel.id
    }
}
