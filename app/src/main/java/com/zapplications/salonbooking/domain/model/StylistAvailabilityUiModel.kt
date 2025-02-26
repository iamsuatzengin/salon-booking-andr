package com.zapplications.salonbooking.domain.model

import com.zapplications.salonbooking.data.response.StylistAvailabilityApiModel

data class StylistAvailabilityUiModel(
    val stylistId: String,
    val availability: List<String>,
    val date: String,
)

fun StylistAvailabilityApiModel.toUiModel() = StylistAvailabilityUiModel(
    stylistId = stylistId.orEmpty(),
    availability = availability.orEmpty(),
    date = date.orEmpty()
)
