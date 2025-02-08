package com.zapplications.salonbooking.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomePageApiModel(
    @SerialName("banner_data") val banner: BannerApiModel? = null,
    @SerialName("categories") val categories: List<CategoryApiModel?>? = null,
    @SerialName("salons") val salons: List<SalonApiModel?>? = null
)
