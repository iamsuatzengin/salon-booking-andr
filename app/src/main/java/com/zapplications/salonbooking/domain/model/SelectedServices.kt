package com.zapplications.salonbooking.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SelectedServices(
    @SerialName("service_name") val serviceName: String,
    @SerialName("service_price") val servicePrice: Double
)
