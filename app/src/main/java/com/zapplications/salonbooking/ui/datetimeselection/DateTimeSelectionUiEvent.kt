package com.zapplications.salonbooking.ui.datetimeselection

import com.zapplications.salonbooking.core.adapter.Item
import com.zapplications.salonbooking.domain.model.datetime.DateUiModel

sealed interface DateTimeSelectionUiEvent {
    data class SelectTime(
        val uiItems: List<Item>
    ) : DateTimeSelectionUiEvent

    data class SelectDate(
        val selectedDate: DateUiModel? = null
    ) : DateTimeSelectionUiEvent
}
