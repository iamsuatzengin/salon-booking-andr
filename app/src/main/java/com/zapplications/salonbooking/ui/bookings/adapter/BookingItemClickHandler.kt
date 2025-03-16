package com.zapplications.salonbooking.ui.bookings.adapter

import com.zapplications.salonbooking.domain.model.BookingsUiModel

interface BookingItemClickHandler {
    fun onViewReceiptClick(item: BookingsUiModel)
    fun onCancelBookingClick(item: BookingsUiModel)
}
