package com.zapplications.salonbooking.domain.model

import com.zapplications.salonbooking.data.response.HomePageApiModel

data class HomePageUiModel(
    val banner: BannerUiModel? = null,
    val categories: List<ServiceCategoryUiModel?>? = null,
    val salons: List<SalonUiModel?>? = null
)

fun HomePageApiModel.toUiModel() = HomePageUiModel(
    banner = banner?.toUiModel(),
    categories = categories?.map { it?.toUiModel() },
    salons = salons?.map { it?.toUiModel() }
)
