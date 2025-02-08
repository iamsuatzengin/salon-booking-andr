package com.zapplications.salonbooking.ui.home.adapter.viewholder

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.zapplications.salonbooking.databinding.ItemViewTitleBinding
import com.zapplications.salonbooking.ui.home.adapter.item.TitleViewItem

class TitleViewHolder(
    private val binding: ItemViewTitleBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(titleViewItem: TitleViewItem) = with(binding) {
        tvTitle.text = titleViewItem.title
        titleViewItem.actionText?.let {
            ivActionIcon.isVisible = true
            tvActionText.isVisible = true
            tvActionText.text = it

            tvActionText.setOnClickListener {
                titleViewItem.actionClickHandler?.invoke()
            }
        }
    }
}
