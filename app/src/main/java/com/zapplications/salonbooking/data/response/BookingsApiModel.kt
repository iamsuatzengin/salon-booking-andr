package com.zapplications.salonbooking.data.response

import com.zapplications.salonbooking.domain.model.SelectedServices
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookingsApiModel(
    @SerialName("id") val id: String? = null,
    @SerialName("customer_id") val customerId: String? = null,
    @SerialName("selected_services") val selectedServices: List<SelectedServices>? = null,
    @SerialName("booking_date") val bookingDate: String? = null,
    @SerialName("booking_time") val bookingTime: String? = null,
    @SerialName("final_amount") val finalAmount: Double? = null,
    @SerialName("status") val status: String? = null,
    @SerialName("payment_type") val paymentType: String? = null,
    @SerialName("salon") val salon: SalonBooking? = null,
)

@Serializable
data class SalonBooking(
    @SerialName("salon_name") val salonName: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("address") val address: String? = null,
    @SerialName("image_url") val imageUrl: String? = null,
)
