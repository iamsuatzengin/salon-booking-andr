package com.zapplications.salonbooking.ui.auth.signin

import com.zapplications.salonbooking.core.UiEvent
import com.zapplications.salonbooking.domain.model.SignInType

sealed interface SignInUiEvent: UiEvent {
    data class NavigateToVerifyScreen(val input: String, val signInType: SignInType) : SignInUiEvent
    data object ShowError : SignInUiEvent
}
