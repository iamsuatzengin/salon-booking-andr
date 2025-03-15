package com.zapplications.salonbooking.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zapplications.salonbooking.core.SPLASH_DELAY
import com.zapplications.salonbooking.domain.repository.AuthRepository
import com.zapplications.salonbooking.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<SplashUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        navigateToSignInWithDelay()
    }

    private fun navigateToSignInWithDelay() {
        viewModelScope.launch {
            delay(SPLASH_DELAY)
            if (authRepository.isUserLoggedIn()) {
                val isNavigatedAppPermissionBefore =
                    settingsRepository.isNavigatedToAppPermissionsFlow.first()

                _uiEvent.emit(
                    SplashUiEvent.NavigateToHome(
                        isNavigatedAppPermissionBefore = isNavigatedAppPermissionBefore
                    )
                )
            } else {
                _uiEvent.emit(SplashUiEvent.NavigateToSignIn)
            }
        }
    }
}
