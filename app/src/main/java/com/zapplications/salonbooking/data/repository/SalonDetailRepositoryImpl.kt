package com.zapplications.salonbooking.data.repository

import com.zapplications.salonbooking.data.datasource.remote.SalonDetailRemoteDataSource
import com.zapplications.salonbooking.domain.model.toUiModel
import com.zapplications.salonbooking.domain.repository.SalonDetailRepository
import javax.inject.Inject

class SalonDetailRepositoryImpl @Inject constructor(
    private val salonDetailRemoteDataSource: SalonDetailRemoteDataSource,
) : SalonDetailRepository {

    override suspend fun getSalonDetail(salonId: String) =
        salonDetailRemoteDataSource.getSalonDetail(salonId)?.toUiModel()
}
