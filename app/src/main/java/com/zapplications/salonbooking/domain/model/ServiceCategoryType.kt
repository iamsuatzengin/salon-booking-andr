package com.zapplications.salonbooking.domain.model

import androidx.annotation.DrawableRes
import com.zapplications.salonbooking.R

enum class ServiceCategoryType(
    @DrawableRes val icon: Int
) {
    HAIR_CUT(
        icon = R.drawable.ic_haircut
    ),
    NAIL_ART(
        icon = R.drawable.ic_nailart
    ),
    HAIR_STYLING(
        icon = R.drawable.ic_hair_styling
    ),
    HAIR_TREATMENTS(
        icon = R.drawable.ic_hair_treatments
    );

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
