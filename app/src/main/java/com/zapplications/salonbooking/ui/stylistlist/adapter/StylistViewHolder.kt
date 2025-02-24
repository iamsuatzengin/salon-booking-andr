package com.zapplications.salonbooking.ui.stylistlist.adapter

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.extensions.loadImage
import com.zapplications.salonbooking.databinding.ItemStylistListBinding
import com.zapplications.salonbooking.domain.model.StylistUiModel

class StylistViewHolder(
    private val binding: ItemStylistListBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(stylistUiModel: StylistUiModel) = with(binding) {
        if (stylistUiModel.isAnyStylist) {
            ivStylistIcon.setImageResource(R.drawable.anystylist)
            tvStylistName.text = root.context.getString(R.string.title_any_stylist)
        } else {
            root.context.loadImage(
                url = stylistUiModel.imageUrl,
                target = ivStylistIcon,
                radius = root.context.resources.getDimensionPixelSize(R.dimen.corner_radius_default)
            )
        }

        tvStylistName.text = if (stylistUiModel.isAnyStylist) {
            root.context.getString(R.string.title_any_stylist)
        } else {
            stylistUiModel.fullName
        }

        tvStylistSpecialization.text = if (stylistUiModel.isAnyStylist) {
            root.context.getString(R.string.text_next_available_stylist)
        } else {
            stylistUiModel.specialization
        }

        llTopRated.isVisible = stylistUiModel.isTopRated
    }
}
