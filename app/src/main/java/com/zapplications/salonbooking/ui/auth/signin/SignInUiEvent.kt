package com.zapplications.salonbooking.ui.auth.signin

import com.zapplications.salonbooking.domain.model.SignInType

sealed interface SignInUiEvent {
    data class NavigateToVerifyScreen(val input: String, val signInType: SignInType) : SignInUiEvent
    data object ShowError : SignInUiEvent
}
