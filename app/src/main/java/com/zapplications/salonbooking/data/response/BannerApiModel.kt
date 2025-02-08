package com.zapplications.salonbooking.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @param buttonBgColor button background color HEX code like **#235AFF**
 * @param buttonTextColor button text color HEX code **#235AFF**
 * @param layerBlurColor layer blur color HEX code **#235AFF**
 */
@Serializable
data class BannerApiModel(
    @SerialName("id") val id: String? = null,
    @SerialName("info") val info: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("campaign") val campaign: String? = null,
    @SerialName("button_text") val buttonText: String? = null,
    @SerialName("bg_image_url") val bgImageUrl: String? = null,
    @SerialName("button_bg_color") val buttonBgColor: String? = null,
    @SerialName("layer_blur_color") val layerBlurColor: String? = null,
    @SerialName("button_text_color") val buttonTextColor: String? = null,
    @SerialName("button_is_available") val buttonIsAvailable: Boolean? = false
)
