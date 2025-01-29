package com.zapplications.salonbooking.ui.splash

sealed interface SplashUiEvent {
    data object NavigateToSignIn : SplashUiEvent
    data object NavigateToHome : SplashUiEvent
}
