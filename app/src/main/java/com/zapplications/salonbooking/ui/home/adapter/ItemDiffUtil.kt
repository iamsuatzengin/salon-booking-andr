package com.zapplications.salonbooking.ui.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.zapplications.salonbooking.core.adapter.Item

class ItemDiffUtil : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
        oldItem.areItemsTheSame(newItem)

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean =
        oldItem.areContentsTheSame(newItem)
}
