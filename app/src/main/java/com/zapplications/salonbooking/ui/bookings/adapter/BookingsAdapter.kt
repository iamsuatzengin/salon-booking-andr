package com.zapplications.salonbooking.ui.bookings.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.zapplications.salonbooking.databinding.ItemBookingsListBinding
import com.zapplications.salonbooking.domain.model.BookingsUiModel

class BookingsAdapter(
    private val clickHandler: BookingItemClickHandler,
) : ListAdapter<BookingsUiModel, BookingsViewHolder>(BookingsDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingsViewHolder {
        val binding = ItemBookingsListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return BookingsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookingsViewHolder, position: Int) {
        holder.bind(item = getItem(position), clickHandler = clickHandler)
    }
}
