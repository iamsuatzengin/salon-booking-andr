package com.zapplications.salonbooking.ui.stylistlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.zapplications.salonbooking.databinding.ItemStylistListBinding
import com.zapplications.salonbooking.domain.model.StylistUiModel

class StylistAdapter(
    private val onStylistClick: (StylistUiModel) -> Unit,
) : ListAdapter<StylistUiModel, StylistViewHolder>(StylistDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StylistViewHolder {
        val binding = ItemStylistListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StylistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StylistViewHolder, position: Int) = holder.bind(
        stylistUiModel = getItem(position),
        onStylistClick = onStylistClick
    )
}
