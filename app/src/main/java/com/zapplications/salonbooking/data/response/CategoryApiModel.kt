package com.zapplications.salonbooking.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryApiModel(
    @SerialName("id") val id: String? = null,
    @SerialName("type_enum") val type: String? = null,
    @SerialName("category_name") val categoryName: String? = null
)
