package com.zapplications.salonbooking.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zapplications.salonbooking.databinding.ItemViewBannerBinding
import com.zapplications.salonbooking.databinding.ItemViewHomeTopBinding
import com.zapplications.salonbooking.databinding.ItemViewNearbySalonsBinding
import com.zapplications.salonbooking.databinding.ItemViewSearchHomeBinding
import com.zapplications.salonbooking.databinding.ItemViewTitleBinding
import com.zapplications.salonbooking.ui.home.adapter.item.BannerViewItem
import com.zapplications.salonbooking.ui.home.adapter.item.Item
import com.zapplications.salonbooking.ui.home.adapter.item.NearbySalonViewItem
import com.zapplications.salonbooking.ui.home.adapter.item.SearchViewItem
import com.zapplications.salonbooking.ui.home.adapter.item.TitleViewItem
import com.zapplications.salonbooking.ui.home.adapter.item.TopViewItem
import com.zapplications.salonbooking.ui.home.adapter.viewholder.BannerViewHolder
import com.zapplications.salonbooking.ui.home.adapter.viewholder.NearbySalonViewHolder
import com.zapplications.salonbooking.ui.home.adapter.viewholder.SearchViewHolder
import com.zapplications.salonbooking.ui.home.adapter.viewholder.TitleViewHolder
import com.zapplications.salonbooking.ui.home.adapter.viewholder.TopViewHolder

class HomeAdapter : ListAdapter<Item, RecyclerView.ViewHolder>(HomeDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            0 -> TopViewHolder(ItemViewHomeTopBinding.inflate(inflater, parent, false))
            1 -> SearchViewHolder(ItemViewSearchHomeBinding.inflate(inflater, parent, false))
            2 -> BannerViewHolder(ItemViewBannerBinding.inflate(inflater, parent, false))
            3 -> TitleViewHolder(ItemViewTitleBinding.inflate(inflater, parent, false))
            else -> NearbySalonViewHolder(ItemViewNearbySalonsBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TopViewHolder -> holder.bind(getItem(position) as TopViewItem)
            is SearchViewHolder -> holder.bind(getItem(position) as SearchViewItem)
            is BannerViewHolder -> holder.bind(getItem(position) as BannerViewItem)
            is TitleViewHolder -> holder.bind(getItem(position) as TitleViewItem)
            is NearbySalonViewHolder -> holder.bind(getItem(position) as NearbySalonViewItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type
    }
}

class HomeDiffUtil : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.areItemsTheSame(oldItem, newItem) == newItem.areItemsTheSame(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.areContentsTheSame(oldItem, newItem) == newItem.areContentsTheSame(oldItem, newItem)
    }
}
