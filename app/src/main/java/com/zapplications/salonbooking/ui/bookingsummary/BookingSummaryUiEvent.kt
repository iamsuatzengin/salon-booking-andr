package com.zapplications.salonbooking.ui.bookingsummary

import com.zapplications.salonbooking.domain.model.BookingAppointmentUiModel

sealed interface BookingSummaryUiEvent {
    data class BookingAppointmentSuccessFull(
        val bookingAppointmentUiModel: BookingAppointmentUiModel
    ) : BookingSummaryUiEvent

    data object ShowError : BookingSummaryUiEvent
}
