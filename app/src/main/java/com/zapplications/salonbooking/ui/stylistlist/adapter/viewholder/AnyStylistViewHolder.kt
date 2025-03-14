package com.zapplications.salonbooking.ui.stylistlist.adapter.viewholder

import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.databinding.ItemAnystylistListBinding
import com.zapplications.salonbooking.ui.stylistlist.adapter.item.AnyStylistViewItem

class AnyStylistViewHolder(
    private val binding: ItemAnystylistListBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(anyStylistViewItem: AnyStylistViewItem) = with(binding) {
        tvAnyStylistTitle.text = root.context.getString(R.string.title_any_stylist)
        tvAnyStylistDescription.text = root.context.getString(R.string.text_next_available_stylist)

        container.background = if (anyStylistViewItem.isSelected) {
            AppCompatResources.getDrawable(root.context, R.drawable.bg_stylist_item_selected)
        } else {
            AppCompatResources.getDrawable(root.context, R.drawable.bg_stylist_item)
        }

        root.setOnClickListener {
            anyStylistViewItem.onClick(anyStylistViewItem, adapterPosition)
        }
    }
}
