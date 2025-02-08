package com.zapplications.salonbooking.data.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject

@Serializable
data class HomePageDataRequest(
    @SerialName("search_latitude") val latitude: Double,
    @SerialName("search_longitude") val longitude: Double,
    @SerialName("radius_meters") val radius: Int = 5000,
    @SerialName("result_limit") val resultLimit: Int = 20
) {

    val jsonObject get() = Json.encodeToJsonElement(this).jsonObject
}
