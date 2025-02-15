package com.zapplications.salonbooking.core.ui.tabview.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zapplications.salonbooking.databinding.ItemSalonServiceBinding
import com.zapplications.salonbooking.domain.model.ServiceUiModel

class TabAdapter : RecyclerView.Adapter<TabViewHolder>() {
    private val serviceList: MutableList<ServiceUiModel> = mutableListOf()
    var onItemClicked: ((ServiceUiModel, position: Int) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<ServiceUiModel>) {
        serviceList.clear()
        serviceList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabViewHolder {
        val binding = ItemSalonServiceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TabViewHolder(binding)
    }

    override fun getItemCount(): Int = serviceList.size

    override fun onBindViewHolder(holder: TabViewHolder, position: Int) {
        holder.bind(serviceList[position], onItemClicked)
    }
}
