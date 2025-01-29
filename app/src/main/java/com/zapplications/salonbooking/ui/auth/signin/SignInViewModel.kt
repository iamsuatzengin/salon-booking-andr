package com.zapplications.salonbooking.ui.auth.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zapplications.salonbooking.domain.model.SignInType
import com.zapplications.salonbooking.domain.usecase.EmailOrNumberCheckerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val emailOrNumberCheckerUseCase: EmailOrNumberCheckerUseCase
) : ViewModel() {
    private val _uiEvent = MutableSharedFlow<SignInUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun handleContinue(input: String) {
        viewModelScope.launch {
            val signInType = emailOrNumberCheckerUseCase(input)

            if (signInType == SignInType.Invalid) {
                _uiEvent.emit(SignInUiEvent.ShowError)
                return@launch
            }

            _uiEvent.emit(SignInUiEvent.NavigateToVerifyScreen(input, signInType))
        }
    }
}
