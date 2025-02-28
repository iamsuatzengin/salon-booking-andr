package com.zapplications.salonbooking.ui.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.zapplications.salonbooking.core.adapter.Item

class ItemDiffUtil : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.areItemsTheSame(oldItem, newItem) == newItem.areItemsTheSame(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.areContentsTheSame(oldItem, newItem) == newItem.areContentsTheSame(oldItem, newItem)
    }
}
