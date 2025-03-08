package com.zapplications.salonbooking.data.response

import com.zapplications.salonbooking.domain.model.SelectedServices
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookingAppointmentApiModel(
    @SerialName("booking_id") val bookingId: String? = null,
    @SerialName("salon_name") val salonName: String? = null,
    @SerialName("stylist_name") val stylistName: String? = null,
    @SerialName("booking_date") val bookingDate: String? = null,
    @SerialName("booking_time") val bookingTime: String? = null,
    @SerialName("final_amount") val finalAmount: Double? = null,
    @SerialName("payment_type") val paymentType: String? = null,
    @SerialName("status") val status: String? = null,
    @SerialName("selected_services") val selectedServices: List<SelectedServices>? = null
)
