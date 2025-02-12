package com.zapplications.salonbooking.domain.model

import com.zapplications.salonbooking.core.extensions.orZero
import com.zapplications.salonbooking.data.response.ServiceApiModel

data class ServiceUiModel(
    val serviceId: String,
    val serviceName: String,
    val defaultPrice: Double,
    val customPrice: Double,
    val defaultDuration: String,
    val customDuration: String,
    val category: ServiceCategoryUiModel
)

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
