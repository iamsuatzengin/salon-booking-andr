package com.zapplications.salonbooking.ui.splash

sealed interface SplashUiEvent {
    data object NavigateToSignIn : SplashUiEvent
    data class NavigateToHome(
        val isNavigatedAppPermissionBefore: Boolean
    ) : SplashUiEvent
}
