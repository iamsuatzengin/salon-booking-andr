package com.zapplications.salonbooking.ui.auth.signin

import com.zapplications.salonbooking.core.ui.BaseViewModel
import com.zapplications.salonbooking.domain.model.SignInType
import com.zapplications.salonbooking.domain.usecase.EmailOrNumberCheckerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val emailOrNumberCheckerUseCase: EmailOrNumberCheckerUseCase
) : BaseViewModel() {

    fun handleContinue(input: String) {
        callWithoutLoading(
            block = { emailOrNumberCheckerUseCase(input) },
            onSuccess = { signInType ->
                if (signInType == SignInType.Invalid) {
                    sendEvent(SignInUiEvent.ShowError)
                    return@callWithoutLoading
                }

                sendEvent(SignInUiEvent.NavigateToVerifyScreen(input, signInType))
            }
        )
    }
}
