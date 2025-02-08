package com.zapplications.salonbooking.domain.repository

import com.zapplications.salonbooking.domain.model.HomePageUiModel

interface HomeRepository {
    suspend fun getAllHomePageData(): HomePageUiModel?
    suspend fun getHomePageDataByLocation(
        longitude: Double,
        latitude: Double
    ): HomePageUiModel?
}
