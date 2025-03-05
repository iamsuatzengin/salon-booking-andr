package com.zapplications.salonbooking.ui.home.adapter.item

import com.zapplications.salonbooking.core.adapter.Item
import com.zapplications.salonbooking.domain.model.BannerUiModel

data class BannerViewItem(
    val bannerUiModel: BannerUiModel,
    val bookNowClick: (() -> Unit)? = null,
) : Item() {
    override val type: Int get() = 2
    override var marginBottomPx: Int = 32

    override fun areContentsTheSame(new: Item): Boolean = this == (new as? BannerViewItem)

    override fun areItemsTheSame(new: Item): Boolean {
        val oldItem = this
        val newItem = new as? BannerViewItem ?: return false
        return oldItem.bannerUiModel == newItem.bannerUiModel && oldItem.bannerUiModel.id == newItem.bannerUiModel.id
    }
}
