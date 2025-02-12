package com.zapplications.salonbooking.data.repository

import com.zapplications.salonbooking.data.datasource.remote.HomePageRemoteDataSource
import com.zapplications.salonbooking.domain.model.HomePageUiModel
import com.zapplications.salonbooking.domain.model.toUiModel
import com.zapplications.salonbooking.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val remoteDataSource: HomePageRemoteDataSource
) : HomeRepository {

    override suspend fun getAllHomePageData(): HomePageUiModel? = remoteDataSource.getAllHomePageData()?.toUiModel()

    override suspend fun getHomePageDataByLocation(
        longitude: Double,
        latitude: Double,
    ): HomePageUiModel? = remoteDataSource.getAllHomePageData()?.toUiModel()
}
