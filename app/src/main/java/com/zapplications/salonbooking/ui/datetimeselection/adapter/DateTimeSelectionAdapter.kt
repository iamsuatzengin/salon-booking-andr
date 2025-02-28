package com.zapplications.salonbooking.ui.datetimeselection.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zapplications.salonbooking.core.adapter.BaseItemAdapter
import com.zapplications.salonbooking.databinding.ItemViewSelectDateRowBinding
import com.zapplications.salonbooking.databinding.ItemViewSelectTimeBinding
import com.zapplications.salonbooking.databinding.ItemViewTitleBinding
import com.zapplications.salonbooking.ui.datetimeselection.adapter.item.SelectDateViewItem
import com.zapplications.salonbooking.ui.datetimeselection.adapter.item.SelectTimeViewItem
import com.zapplications.salonbooking.ui.datetimeselection.adapter.viewholder.SelectDateViewHolder
import com.zapplications.salonbooking.ui.datetimeselection.adapter.viewholder.SelectTimeViewHolder
import com.zapplications.salonbooking.ui.home.adapter.item.TitleViewItem
import com.zapplications.salonbooking.ui.home.adapter.viewholder.TitleViewHolder

class DateTimeSelectionAdapter : BaseItemAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            1 -> SelectDateViewHolder(ItemViewSelectDateRowBinding.inflate(inflater, parent, false))
            2 -> SelectTimeViewHolder(ItemViewSelectTimeBinding.inflate(inflater, parent, false))
            3 -> TitleViewHolder(ItemViewTitleBinding.inflate(inflater, parent, false))
            else -> TitleViewHolder(ItemViewTitleBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SelectDateViewHolder -> holder.bind(getItem(position) as SelectDateViewItem)
            is SelectTimeViewHolder -> holder.bind(getItem(position) as SelectTimeViewItem)
            is TitleViewHolder -> holder.bind(getItem(position) as TitleViewItem)
        }
    }
}
