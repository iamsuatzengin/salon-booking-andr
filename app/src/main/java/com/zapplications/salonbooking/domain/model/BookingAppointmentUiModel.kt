package com.zapplications.salonbooking.domain.model

import android.os.Parcelable
import com.zapplications.salonbooking.core.extensions.orZero
import com.zapplications.salonbooking.data.response.BookingAppointmentApiModel
import com.zapplications.salonbooking.domain.model.enums.BookingStatusType
import com.zapplications.salonbooking.domain.model.enums.PaymentType
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class BookingAppointmentUiModel(
    val bookingId: String,
    val salonName: String,
    val stylistName: String,
    val bookingDate: String,
    val bookingTime: String,
    val finalAmount: Double,
    val paymentType: PaymentType,
    val status: BookingStatusType,
    val selectedServices: List<SelectedServices>
) : Parcelable

fun BookingAppointmentApiModel.toUiModel() = BookingAppointmentUiModel(
    bookingId = bookingId.orEmpty(),
    salonName = salonName.orEmpty(),
    stylistName = stylistName.orEmpty(),
    bookingDate = bookingDate.orEmpty(),
    bookingTime = bookingTime.orEmpty(),
    finalAmount = finalAmount.orZero(),
    paymentType = PaymentType.fromString(paymentType),
    status = BookingStatusType.fromString(status),
    selectedServices = selectedServices.orEmpty()
)
