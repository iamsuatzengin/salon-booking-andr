package com.zapplications.salonbooking.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StylistAvailabilityApiModel(
    @SerialName("stylist_id") val stylistId: String? = null,
    @SerialName("booked_slots") val availability: List<String>? = null,
    @SerialName("date") val date: String? = null,
)
