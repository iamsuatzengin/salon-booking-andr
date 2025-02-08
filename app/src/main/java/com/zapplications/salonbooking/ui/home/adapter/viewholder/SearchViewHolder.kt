package com.zapplications.salonbooking.ui.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.zapplications.salonbooking.databinding.ItemViewSearchHomeBinding
import com.zapplications.salonbooking.ui.home.adapter.item.SearchViewItem

class SearchViewHolder(
    private val binding: ItemViewSearchHomeBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(searchViewItem: SearchViewItem) {
        binding.etSearch.hint = searchViewItem.hint
    }
}
