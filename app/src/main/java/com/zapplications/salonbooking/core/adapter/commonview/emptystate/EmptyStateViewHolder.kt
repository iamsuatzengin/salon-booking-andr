package com.zapplications.salonbooking.core.adapter.commonview.emptystate

import androidx.recyclerview.widget.RecyclerView
import com.zapplications.salonbooking.databinding.ItemViewEmptyStateBinding

class EmptyStateViewHolder(
    private val binding: ItemViewEmptyStateBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(viewItem: EmptyStateViewItem) = with(binding) {
        ivEmptyState.setImageResource(viewItem.imageRes)
        tvEmptyStateTitle.text = viewItem.title
        tvEmptyStateBody.text = viewItem.body
    }
}
