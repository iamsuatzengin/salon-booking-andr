package com.zapplications.salonbooking.domain.model

import com.zapplications.salonbooking.data.response.CategoryApiModel

data class ServiceCategoryUiModel(
    val id: String,
    val type: ServiceCategoryType,
    val categoryName: String,
    var isSelected: Boolean = false
)

fun CategoryApiModel.toUiModel() = ServiceCategoryUiModel(
    id = id.orEmpty(),
    type = ServiceCategoryType.fromString(type.orEmpty()) ?: ServiceCategoryType.HAIR_CUT,
    categoryName = categoryName.orEmpty()
)
