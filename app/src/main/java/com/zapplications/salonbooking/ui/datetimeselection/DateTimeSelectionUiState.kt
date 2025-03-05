package com.zapplications.salonbooking.ui.datetimeselection

import com.zapplications.salonbooking.core.adapter.Item

data class DateTimeSelectionUiState(
    val items: List<Item> = emptyList(),
    val isConfirmButtonEnabled: Boolean = false,
)
