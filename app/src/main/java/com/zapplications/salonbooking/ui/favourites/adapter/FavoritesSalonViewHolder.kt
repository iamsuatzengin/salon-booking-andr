package com.zapplications.salonbooking.ui.favourites.adapter

import androidx.recyclerview.widget.RecyclerView
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.extensions.CORNER_RADIUS_DEFAULT_PX
import com.zapplications.salonbooking.core.extensions.loadImage
import com.zapplications.salonbooking.data.local.entity.FavoriteSalonEntity
import com.zapplications.salonbooking.databinding.ItemViewNearbySalonsBinding

class FavoritesSalonViewHolder(
    private val binding: ItemViewNearbySalonsBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(uiModel: FavoriteSalonEntity) = with(binding) {
        tvSalonName.text = uiModel.salonName
        tvSalonLocationString.text = uiModel.address
        tvRating.text = root.context.getString(
            R.string.text_rating,
            uiModel.rating,
            uiModel.reviewerCount
        )

        root.context.loadImage(
            url = uiModel.imageUrl,
            target = ivSalonImage,
            radius = CORNER_RADIUS_DEFAULT_PX
        )
    }
}
