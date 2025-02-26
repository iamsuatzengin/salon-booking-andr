package com.zapplications.salonbooking.data.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject

@Serializable
data class StylistAvailabilityRequest(
    @SerialName("p_stylist_id") val stylistId: String,
    @SerialName("p_date") val date: String,
) {
    val jsonObject get() = Json.encodeToJsonElement(this).jsonObject
}
