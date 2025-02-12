package com.zapplications.salonbooking.ui.home

sealed interface HomeUiEvent {
    class NavigateToSalonDetail(val salonId: String) : HomeUiEvent
}
