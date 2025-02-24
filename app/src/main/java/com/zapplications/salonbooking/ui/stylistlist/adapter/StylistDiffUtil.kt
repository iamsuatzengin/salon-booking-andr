package com.zapplications.salonbooking.ui.stylistlist.adapter

import androidx.recyclerview.widget.DiffUtil
import com.zapplications.salonbooking.domain.model.StylistUiModel

class StylistDiffUtil : DiffUtil.ItemCallback<StylistUiModel>() {
    override fun areItemsTheSame(oldItem: StylistUiModel, newItem: StylistUiModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: StylistUiModel, newItem: StylistUiModel): Boolean {
        return oldItem.id == newItem.id
                && oldItem.salonId == newItem.salonId
    }
}
