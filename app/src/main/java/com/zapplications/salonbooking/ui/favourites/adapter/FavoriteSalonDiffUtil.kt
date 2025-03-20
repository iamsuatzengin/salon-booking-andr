package com.zapplications.salonbooking.ui.favourites.adapter

import androidx.recyclerview.widget.DiffUtil
import com.zapplications.salonbooking.data.local.entity.FavoriteSalonEntity

class FavoriteSalonDiffUtil : DiffUtil.ItemCallback<FavoriteSalonEntity>() {
    override fun areItemsTheSame(
        oldItem: FavoriteSalonEntity,
        newItem: FavoriteSalonEntity,
    ): Boolean = oldItem.id == newItem.id && oldItem.salonName == newItem.salonName

    override fun areContentsTheSame(
        oldItem: FavoriteSalonEntity,
        newItem: FavoriteSalonEntity,
    ): Boolean = oldItem == newItem
}
