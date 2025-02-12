package com.zapplications.salonbooking.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ServiceApiModel(
    @SerialName("service_id") val serviceId: String? = null,
    @SerialName("service_name") val serviceName: String? = null,
    @SerialName("default_price") val defaultPrice: Double? = null,
    @SerialName("custom_price") val customPrice: Double? = null,
    @SerialName("default_duration") val defaultDuration: String? = null,
    @SerialName("custom_duration") val customDuration: String? = null,
    @SerialName("category") val category: CategoryApiModel? = null
)
