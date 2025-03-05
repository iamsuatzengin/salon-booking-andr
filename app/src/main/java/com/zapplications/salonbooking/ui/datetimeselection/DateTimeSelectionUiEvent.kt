package com.zapplications.salonbooking.ui.datetimeselection

sealed interface DateTimeSelectionUiEvent {
    data object MoreDate: DateTimeSelectionUiEvent
}
