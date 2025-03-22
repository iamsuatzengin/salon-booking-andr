package com.zapplications.salonbooking.ui.bookings

import com.zapplications.salonbooking.core.UiEvent
import com.zapplications.salonbooking.domain.model.BookingAppointmentUiModel

data class NavigateToReceipt(
    val bookingAppointmentUiModel: BookingAppointmentUiModel
) : UiEvent

data object BookingCancelledSuccess : UiEvent