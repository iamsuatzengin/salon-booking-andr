package com.zapplications.salonbooking.ui.bookingsummary

import com.zapplications.salonbooking.core.UiEvent
import com.zapplications.salonbooking.domain.model.BookingAppointmentUiModel

sealed interface BookingSummaryUiEvent: UiEvent {
    data class BookingAppointmentSuccessFull(
        val bookingAppointmentUiModel: BookingAppointmentUiModel
    ) : BookingSummaryUiEvent

    data class ShowError(val message: String) : BookingSummaryUiEvent
}
