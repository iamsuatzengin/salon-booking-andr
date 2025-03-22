package com.zapplications.salonbooking.ui.stylistlist

import com.zapplications.salonbooking.core.UiState
import com.zapplications.salonbooking.core.adapter.Item

data class StylistListUiState(
    val uiItems: List<Item> = emptyList(),
    val buttonEnabled: Boolean = false,
) : UiState
