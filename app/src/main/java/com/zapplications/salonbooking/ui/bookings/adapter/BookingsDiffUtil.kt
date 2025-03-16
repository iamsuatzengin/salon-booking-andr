package com.zapplications.salonbooking.ui.bookings.adapter

import androidx.recyclerview.widget.DiffUtil
import com.zapplications.salonbooking.domain.model.BookingsUiModel

class BookingsDiffUtil : DiffUtil.ItemCallback<BookingsUiModel>() {
    override fun areItemsTheSame(oldItem: BookingsUiModel, newItem: BookingsUiModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: BookingsUiModel, newItem: BookingsUiModel): Boolean =
        oldItem == newItem
}
