package com.zapplications.salonbooking.domain.model

import com.zapplications.salonbooking.data.response.CategoryApiModel

data class ServiceCategoryUiModel(
    val id: String,
    val type: ServiceCategoryType,
    val categoryName: String,
)

fun CategoryApiModel.toUiModel() = ServiceCategoryUiModel(
    id = id.orEmpty(),
    type = ServiceCategoryType.fromString(type.orEmpty()) ?: ServiceCategoryType.HAIR_CUT,
    categoryName = categoryName.orEmpty()
)

enum class ServiceCategoryType {
    HAIR_CUT,
    NAIL_ART,
    HAIR_STYLING,
    HAIR_TREATMENTS;

    companion object {
        fun fromString(type: String): ServiceCategoryType? {
            return when (type) {
                "HAIR_CUT" -> HAIR_CUT
                "NAIL_ART" -> NAIL_ART
                "HAIR_STYLING" -> HAIR_STYLING
                "HAIR_TREATMENTS" -> HAIR_TREATMENTS
                else -> null
            }
        }
    }
}
