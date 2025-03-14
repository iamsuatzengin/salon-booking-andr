package com.zapplications.salonbooking.ui.stylistlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.zapplications.salonbooking.core.adapter.BaseItemAdapter
import com.zapplications.salonbooking.databinding.ItemAnystylistListBinding
import com.zapplications.salonbooking.databinding.ItemStylistListBinding
import com.zapplications.salonbooking.ui.stylistlist.adapter.item.AnyStylistViewItem
import com.zapplications.salonbooking.ui.stylistlist.adapter.item.StylistViewItem
import com.zapplications.salonbooking.ui.stylistlist.adapter.item.StylistViewType
import com.zapplications.salonbooking.ui.stylistlist.adapter.viewholder.AnyStylistViewHolder
import com.zapplications.salonbooking.ui.stylistlist.adapter.viewholder.StylistViewHolder

class StylistAdapter : BaseItemAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            StylistViewType.ANY_STYLIST.ordinal -> AnyStylistViewHolder(
                ItemAnystylistListBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            StylistViewType.STYLIST.ordinal -> {
                StylistViewHolder(
                    ItemStylistListBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            else -> StylistViewHolder(
                ItemStylistListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(holder) {
            is AnyStylistViewHolder -> holder.bind(getItem(position) as AnyStylistViewItem)
            is StylistViewHolder -> holder.bind(getItem(position) as StylistViewItem)
        }
    }
}
