package com.zapplications.salonbooking.ui.home.adapter.viewholder

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.zapplications.salonbooking.databinding.ItemViewBannerBinding
import com.zapplications.salonbooking.ui.home.adapter.item.BannerViewItem

class BannerViewHolder(
    private val binding: ItemViewBannerBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(bannerViewItem: BannerViewItem) {
        binding.apply {
            //ivBgImage.setImageResource(bannerViewItem.bannerUiModel)
            tvTitle.text = bannerViewItem.bannerUiModel.title
            tvCampaign.text = bannerViewItem.bannerUiModel.campaign
            tvInfo.text = bannerViewItem.bannerUiModel.info
            btnBanner.text = bannerViewItem.bannerUiModel.buttonText
            btnBanner.setTextColor(Color.parseColor(bannerViewItem.bannerUiModel.buttonTextColor))
            btnBanner.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor(bannerViewItem.bannerUiModel.buttonBgColor))
            btnBanner.isVisible = bannerViewItem.bannerUiModel.buttonIsAvailable
            btnBanner.setOnClickListener { bannerViewItem.bookNowClick?.invoke() }
        }
    }
}
