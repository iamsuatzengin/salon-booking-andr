package com.zapplications.salonbooking.domain.repository

import com.zapplications.salonbooking.domain.model.SalonUiModel

interface SalonDetailRepository {
    suspend fun getSalonDetail(salonId: String): SalonUiModel?
}
