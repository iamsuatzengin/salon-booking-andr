package com.zapplications.salonbooking.ui.bookingsummary

import com.zapplications.salonbooking.core.UiEvent
import com.zapplications.salonbooking.domain.model.BookingAppointmentUiModel

data class BookingAppointmentSuccessFull(
    val bookingAppointmentUiModel: BookingAppointmentUiModel
) : UiEvent
