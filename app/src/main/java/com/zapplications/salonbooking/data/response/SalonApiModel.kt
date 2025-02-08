package com.zapplications.salonbooking.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SalonApiModel(
    @SerialName("id") val id: String? = null,
    @SerialName("salon_name") val salonName: String? = null,
    @SerialName("rating") val rating: Double? = null,
    @SerialName("address") val address: String? = null,
    @SerialName("latitude") val latitude: Double? = null,
    @SerialName("longitude") val longitude: Double? = null,
    @SerialName("work_days") val workDays: String? = null,
    @SerialName("work_hours") val workHours: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("reviewer_count") val reviewerCount: Int? = null,
    @SerialName("created_at") val createdAt: String? = null
)
