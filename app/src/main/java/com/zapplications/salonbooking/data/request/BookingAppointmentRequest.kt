package com.zapplications.salonbooking.data.request

import com.zapplications.salonbooking.domain.model.SelectedServices
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject

@Serializable
data class BookingAppointmentRequest(
    @SerialName("p_salon_id") val salonId: String,
    @SerialName("p_customer_id") val customerId: String,
    @SerialName("p_stylist_id") val stylistId: String,
    @SerialName("p_booking_date") val bookingDate: String,
    @SerialName("p_booking_time") val bookingTime: String,
    @SerialName("p_total_amount") val totalAmount: Double,
    @SerialName("p_discount_amount") val discountAmount: Int,
    @SerialName("p_final_amount") val finalAmount: Double,
    @SerialName("p_status") val status: String,
    @SerialName("p_payment_type") val paymentType: String,
    @SerialName("p_selected_services") val selectedServices: List<SelectedServices>,
) {
    val jsonObject get() = Json.encodeToJsonElement(this).jsonObject
}
