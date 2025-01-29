package com.zapplications.salonbooking.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zapplications.salonbooking.core.SPLASH_DELAY
import com.zapplications.salonbooking.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _uiEvent = MutableSharedFlow<SplashUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        navigateToSignInWithDelay()
    }

    private fun navigateToSignInWithDelay() {
        viewModelScope.launch {
            delay(SPLASH_DELAY)
            if (authRepository.isUserLoggedIn()) {
                _uiEvent.emit(SplashUiEvent.NavigateToHome)
            } else {
                _uiEvent.emit(SplashUiEvent.NavigateToSignIn)
            }
        }
    }
}
