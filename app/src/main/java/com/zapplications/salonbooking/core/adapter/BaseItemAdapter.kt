package com.zapplications.salonbooking.core.adapter

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zapplications.salonbooking.ui.home.adapter.ItemDiffUtil

abstract class BaseItemAdapter: ListAdapter<Item, RecyclerView.ViewHolder>(ItemDiffUtil()) {
    override fun getItemViewType(position: Int): Int = getItem(position).type
}
