package com.zapplications.salonbooking.domain.model

import com.zapplications.salonbooking.core.extensions.orFalse
import com.zapplications.salonbooking.data.response.StylistApiModel

data class StylistUiModel(
    val id: String,
    val fullName: String,
    val imageUrl: String,
    val specialization: String,
    val isTopRated: Boolean,
    val salonId: String,
    val isSelected: Boolean = false,
    val isAnyStylist: Boolean = false
)

fun StylistApiModel.toUiModel() = StylistUiModel(
    id = id.orEmpty(),
    fullName = fullName.orEmpty(),
    imageUrl = imageUrl.orEmpty(),
    specialization = specialization.orEmpty(),
    isTopRated = isTopRated.orFalse(),
    salonId = salonId.orEmpty()
)
