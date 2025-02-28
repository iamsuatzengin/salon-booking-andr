package com.zapplications.salonbooking.ui.datetimeselection

sealed interface DateTimeSelectionUiEvent {
    data class SelectTime(
        val previousSelectedPosition: Int?,
        val selectedPosition: Int?,
    ) : DateTimeSelectionUiEvent

    data class SelectDate(val dateSelectionPosition: Int) : DateTimeSelectionUiEvent
}
