package com.zapplications.salonbooking.domain.model

import com.zapplications.salonbooking.core.extensions.orZero
import com.zapplications.salonbooking.data.response.SalonApiModel

data class SalonUiModel(
    val id: String,
    val salonName: String,
    val rating: Double,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val workDays: String,
    val workHours: String,
    val description: String,
    val reviewerCount: Int,
    val createdAt: String,
    val imageUrl: String,
    val services: List<ServiceUiModel> = emptyList()
)

fun SalonApiModel.toUiModel() = SalonUiModel(
    id = id.orEmpty(),
    salonName = salonName.orEmpty(),
    rating = rating.orZero(),
    address = address.orEmpty(),
    latitude = latitude.orZero(),
    longitude = longitude.orZero(),
    workDays = workDays.orEmpty(),
    workHours = workHours.orEmpty(),
    description = description.orEmpty(),
    reviewerCount = reviewerCount.orZero(),
    createdAt = createdAt.orEmpty(),
    imageUrl = imageUrl.orEmpty(),
    services = services?.mapNotNull { it?.toUiModel() }.orEmpty()
)
