package com.zapplications.salonbooking.ui.favourites.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.zapplications.salonbooking.data.local.entity.FavoriteSalonEntity
import com.zapplications.salonbooking.databinding.ItemViewNearbySalonsBinding

class FavoritesSalonAdapter :
    ListAdapter<FavoriteSalonEntity, FavoritesSalonViewHolder>(FavoriteSalonDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesSalonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemViewNearbySalonsBinding.inflate(layoutInflater, parent, false)

        return FavoritesSalonViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: FavoritesSalonViewHolder, position: Int) {
        holder.bind(uiModel = getItem(position))
    }
}
