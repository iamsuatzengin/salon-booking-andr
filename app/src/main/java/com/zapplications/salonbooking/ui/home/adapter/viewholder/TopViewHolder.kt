package com.zapplications.salonbooking.ui.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.databinding.ItemViewHomeTopBinding
import com.zapplications.salonbooking.ui.home.adapter.item.TopViewItem

class TopViewHolder(
    private val binding: ItemViewHomeTopBinding
) : ViewHolder(binding.root) {
    fun bind(topViewItem: TopViewItem) {
        binding.tvLocationTitle.text = topViewItem.title.ifEmpty {
            binding.root.context.getString(R.string.title_location)
        }
        binding.tvLocation.text = topViewItem.locationString.ifEmpty {
            binding.root.context.getString(R.string.text_turn_on_location)
        }
        binding.root.setOnClickListener { topViewItem.clickHandler?.invoke() }
    }
}
