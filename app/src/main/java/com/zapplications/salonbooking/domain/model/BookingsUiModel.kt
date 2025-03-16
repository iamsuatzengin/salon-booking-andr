package com.zapplications.salonbooking.domain.model

import com.zapplications.salonbooking.data.response.BookingsApiModel
import com.zapplications.salonbooking.data.response.SalonBooking
import com.zapplications.salonbooking.domain.model.enums.BookingStatusType
import com.zapplications.salonbooking.domain.model.enums.PaymentType

data class BookingsUiModel(
    val id: String,
    val selectedServices: List<SelectedServices>,
    val salon: SalonBooking? = null,
    val status: BookingStatusType,
    val paymentType: PaymentType,
    val bookingDate: String,
    val bookingTime: String
) {
    val isCancelled: Boolean
        get() = status == BookingStatusType.CANCELLED

    val isUpcoming: Boolean
        get() = status == BookingStatusType.UPCOMING
}

fun BookingsApiModel.toUiModel() = BookingsUiModel(
    id = id.orEmpty(),
    selectedServices = selectedServices ?: emptyList(),
    salon = salon,
    status = BookingStatusType.fromString(status.orEmpty()),
    paymentType = PaymentType.fromString(paymentType.orEmpty()),
    bookingDate = bookingDate.orEmpty(),
    bookingTime = bookingTime.orEmpty()
)

fun List<BookingsApiModel>.toUiModel() = map { it.toUiModel() }
