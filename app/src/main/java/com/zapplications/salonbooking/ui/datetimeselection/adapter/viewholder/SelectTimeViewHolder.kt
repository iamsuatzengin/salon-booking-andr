package com.zapplications.salonbooking.ui.datetimeselection.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.zapplications.salonbooking.databinding.ItemViewSelectTimeBinding
import com.zapplications.salonbooking.ui.datetimeselection.adapter.item.SelectTimeViewItem

class SelectTimeViewHolder(
    private val binding: ItemViewSelectTimeBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(selectTimeViewItem: SelectTimeViewItem) {
        binding.apply {
            val amPm = if (selectTimeViewItem.timeUiModel.isAM) "AM" else "PM"
            tvTime.text = selectTimeViewItem.timeUiModel.time.toString() + " " + amPm
            tvDiscount.text = selectTimeViewItem.discount.toString()
            root.setOnClickListener {
                selectTimeViewItem.clickHandler(selectTimeViewItem.timeUiModel)
            }
        }
    }
}