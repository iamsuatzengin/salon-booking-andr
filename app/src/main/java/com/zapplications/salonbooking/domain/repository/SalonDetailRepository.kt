package com.zapplications.salonbooking.domain.repository

import com.zapplications.salonbooking.domain.model.SalonUiModel
import com.zapplications.salonbooking.domain.model.StylistUiModel

interface SalonDetailRepository {
    suspend fun getSalonDetail(salonId: String): SalonUiModel?
    suspend fun getStylistsBySalonId(salonId: String): List<StylistUiModel>?
}
