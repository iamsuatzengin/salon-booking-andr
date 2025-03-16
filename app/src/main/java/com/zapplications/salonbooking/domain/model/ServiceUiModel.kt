package com.zapplications.salonbooking.domain.model

import androidx.annotation.DrawableRes
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.extensions.orZero
import com.zapplications.salonbooking.data.response.ServiceApiModel
import com.zapplications.salonbooking.domain.model.enums.ServiceCategoryType

data class ServiceUiModel(
    val serviceId: String,
    val serviceName: String,
    val defaultPrice: Double,
    val customPrice: Double,
    val defaultDuration: String,
    val customDuration: String,
    val category: ServiceCategoryUiModel
) {
    val price: Double
        get() = if (customPrice > 0) customPrice else defaultPrice

    val duration : String
        get() = customDuration.ifEmpty { defaultDuration }

    var selected: Boolean = false

    @get:DrawableRes
    val selectedIcon: Int
        get() = if (selected) R.drawable.ic_check_circle else R.drawable.ic_add
}

fun ServiceApiModel.toUiModel() = ServiceUiModel(
    serviceId = serviceId.orEmpty(),
    serviceName = serviceName.orEmpty(),
    defaultPrice = defaultPrice.orZero(),
    customPrice = customPrice.orZero(),
    defaultDuration = defaultDuration.orEmpty(),
    customDuration = customDuration.orEmpty(),
    category = category?.toUiModel() ?: ServiceCategoryUiModel(
        id = "",
        type = ServiceCategoryType.HAIR_CUT,
        categoryName = ""
    )
)
