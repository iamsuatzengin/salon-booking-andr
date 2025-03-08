package com.zapplications.salonbooking.ui.bookingsummary

import com.zapplications.salonbooking.domain.model.BookingAppointmentUiModel

sealed interface BookingSummaryUiEvent {
    data class BookingAppointmentSuccessFull(
        val bookingAppointmentUiModel: BookingAppointmentUiModel
    ) : BookingSummaryUiEvent

    class ShowError(val message: String) : BookingSummaryUiEvent
}
