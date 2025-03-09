package com.zapplications.salonbooking.ui.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.databinding.ItemViewNearbySalonsBinding
import com.zapplications.salonbooking.ui.home.adapter.item.NearbySalonViewItem

class NearbySalonViewHolder(
    private val binding: ItemViewNearbySalonsBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(nearbySalonViewItem: NearbySalonViewItem) = with(binding) {
        nearbySalonViewItem.salonUiModel.run {
            tvSalonName.text = salonName
            tvSalonLocationString.text = address
            tvRating.text = root.context.getString(
                R.string.text_rating,
                rating,
                reviewerCount
            )

            root.setOnClickListener {
                nearbySalonViewItem.clickHandler(id)
            }
        }
    }
}
