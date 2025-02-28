package com.zapplications.salonbooking.domain.model.datetime

import kotlinx.datetime.LocalTime

data class TimeUiModel(
    val time: LocalTime,
    val isAM: Boolean,
)
