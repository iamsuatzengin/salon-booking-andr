package com.zapplications.salonbooking.ui.stylistlist.adapter.viewholder

import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.extensions.loadImage
import com.zapplications.salonbooking.databinding.ItemStylistListBinding
import com.zapplications.salonbooking.ui.stylistlist.adapter.item.StylistViewItem

class StylistViewHolder(
    private val binding: ItemStylistListBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(stylistViewItem: StylistViewItem) = with(binding) {
        root.setOnClickListener {
            stylistViewItem.onClick(stylistViewItem, adapterPosition)
        }

        container.background = if (stylistViewItem.isSelected) {
            AppCompatResources.getDrawable(root.context, R.drawable.bg_stylist_item_selected)
        } else {
            AppCompatResources.getDrawable(root.context, R.drawable.bg_stylist_item)
        }

        root.context.loadImage(
            url = stylistViewItem.stylistUiModel.imageUrl,
            target = ivStylistIcon,
            radius = root.context.resources.getDimensionPixelSize(R.dimen.corner_radius_default)
        )

        tvStylistName.text = stylistViewItem.stylistUiModel.fullName
        tvStylistSpecialization.text = stylistViewItem.stylistUiModel.specialization
        llTopRated.isVisible = stylistViewItem.stylistUiModel.isTopRated
    }
}
