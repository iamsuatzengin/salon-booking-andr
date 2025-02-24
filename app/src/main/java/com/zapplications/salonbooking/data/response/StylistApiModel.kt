package com.zapplications.salonbooking.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StylistApiModel(
    @SerialName("id") val id: String? = null,
    @SerialName("full_name") val fullName: String? = null,
    @SerialName("image_url") val imageUrl: String? = null,
    @SerialName("specialization") val specialization: String? = null,
    @SerialName("is_top_rated") val isTopRated: Boolean? = null,
    @SerialName("salon_id") val salonId: String? = null,
    @SerialName("created_at") val createdAt: String? = null,
)
