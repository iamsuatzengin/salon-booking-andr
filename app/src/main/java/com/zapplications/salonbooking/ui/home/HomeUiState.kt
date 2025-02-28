package com.zapplications.salonbooking.ui.home

import com.zapplications.salonbooking.core.adapter.Item

data class HomeUiState(
    val homePageUiModel: List<Item>? = null,
    val locationString: String = "",
) {
    companion object {
        val Empty = HomeUiState()
    }
}
