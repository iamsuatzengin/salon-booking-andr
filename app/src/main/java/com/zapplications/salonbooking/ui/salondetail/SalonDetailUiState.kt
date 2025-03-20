package com.zapplications.salonbooking.ui.salondetail

import com.zapplications.salonbooking.domain.model.SalonUiModel

data class SalonDetailUiState(
    val salonUiModel: SalonUiModel? = null,
    val isSalonFavorite: Boolean = false
)
