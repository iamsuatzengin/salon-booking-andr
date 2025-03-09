package com.zapplications.salonbooking.ui.datetimeselection.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.extensions.BLANK
import com.zapplications.salonbooking.core.extensions.color
import com.zapplications.salonbooking.databinding.ItemViewSelectTimeBinding
import com.zapplications.salonbooking.ui.datetimeselection.adapter.item.SelectTimeViewItem

class SelectTimeViewHolder(
    private val binding: ItemViewSelectTimeBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(selectTimeViewItem: SelectTimeViewItem) {
        binding.apply {
            val amPm = if (selectTimeViewItem.timeUiModel.isAM) "AM" else "PM"
            tvTime.text = buildString {
                append(selectTimeViewItem.timeUiModel.time.toString())
                append(BLANK)
                append(amPm)
            }
            selectTimeViewItem.discount.toString().also { tvDiscount.text = it }
            root.isEnabled = selectTimeViewItem.isAvailable
            root.setOnClickListener {
                selectTimeViewItem.clickHandler(selectTimeViewItem, adapterPosition)
            }

            if (selectTimeViewItem.isSelected) {
                root.setBackgroundResource(R.drawable.bg_stylist_item_selected)
            } else {
                root.setBackgroundResource(R.drawable.bg_stylist_item)
            }

            tvTime.setTextColor(
                if (selectTimeViewItem.isAvailable) root.resources.color(R.color.color_dark1)
                else root.resources.color(R.color.color_gray1)
            )
        }
    }
}