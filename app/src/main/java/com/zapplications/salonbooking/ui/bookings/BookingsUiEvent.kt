package com.zapplications.salonbooking.ui.bookings

import com.zapplications.salonbooking.domain.model.BookingAppointmentUiModel

sealed interface BookingsUiEvent {
    data class NavigateToReceipt(
        val bookingAppointmentUiModel: BookingAppointmentUiModel
    ) : BookingsUiEvent

    data object ShowError : BookingsUiEvent

    data object BookingCancelledSuccess : BookingsUiEvent
}
