package com.zapplications.salonbooking.domain.model.datetime

data class DateUiModel(
    val formattedDate: String,
    val dayOfWeekText: String,
    val dayOfMonthNumber: Int,
    val month: String,
)
