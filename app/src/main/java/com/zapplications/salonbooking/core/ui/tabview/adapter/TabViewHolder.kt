package com.zapplications.salonbooking.core.ui.tabview.adapter

import androidx.recyclerview.widget.RecyclerView
import com.zapplications.salonbooking.databinding.ItemSalonServiceBinding
import com.zapplications.salonbooking.domain.model.ServiceUiModel

class TabViewHolder(
    private val binding: ItemSalonServiceBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(uiModel: ServiceUiModel, onItemClicked: ((ServiceUiModel, Int) -> Unit)?) {
        binding.tvServiceName.text = uiModel.serviceName
        binding.tvServicePrice.text = uiModel.price.toString()
        binding.tvServiceTime.text = uiModel.duration
        binding.ivAddIcon.setImageResource(uiModel.selectedIcon)

        binding.root.setOnClickListener {
            onItemClicked?.invoke(uiModel, adapterPosition)
        }
    }
}
