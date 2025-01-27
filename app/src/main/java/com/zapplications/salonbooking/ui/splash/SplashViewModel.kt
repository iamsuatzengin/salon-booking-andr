package com.zapplications.salonbooking.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(): ViewModel() {

    private val _uiEvent = MutableSharedFlow<SplashUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        navigateToSignInWithDelay()
    }

    fun navigateToSignInWithDelay() {
        viewModelScope.launch {
            delay(2000)
            _uiEvent.emit(SplashUiEvent.NavigateToSignIn)
        }
    }
}
