package com.zapplications.salonbooking.domain.model

import com.zapplications.salonbooking.core.extensions.orFalse
import com.zapplications.salonbooking.data.response.BannerApiModel

data class BannerUiModel(
    val id: String,
    val info: String,
    val title: String,
    val campaign: String,
    val buttonText: String,
    val bgImageUrl: String,
    val buttonBgColor: String,
    val layerBlurColor: String,
    val buttonTextColor: String,
    val buttonIsAvailable: Boolean
)

fun BannerApiModel.toUiModel() = BannerUiModel(
    id = id.orEmpty(),
    info = info.orEmpty(),
    title = title.orEmpty(),
    campaign = campaign.orEmpty(),
    buttonText = buttonText.orEmpty(),
    bgImageUrl = bgImageUrl.orEmpty(),
    buttonBgColor = buttonBgColor.orEmpty(),
    layerBlurColor = layerBlurColor.orEmpty(),
    buttonTextColor = buttonTextColor.orEmpty(),
    buttonIsAvailable = buttonIsAvailable.orFalse(),
)
