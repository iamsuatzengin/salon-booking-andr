package com.zapplications.salonbooking.ui.auth.verify

import androidx.lifecycle.SavedStateHandle
import com.zapplications.salonbooking.core.ui.BaseViewModel
import com.zapplications.salonbooking.domain.model.SignInType
import com.zapplications.salonbooking.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class VerifyViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val authRepository: AuthRepository,
) : BaseViewModel() {

    val authenticationState = authRepository.sessionStatusStateFlow

    val emailOrPhoneNumber = savedStateHandle.get<String>(EMAIL_OR_PHONE_NUMBER_KEY)
    val signInType = savedStateHandle.get<SignInType>(INPUT_TYPE_KEY) ?: SignInType.Invalid

    var otpCode: String? = null

    /**
     *TODO: check request OTP delay
     * "AuthRestException: For security purposes, you can only request this after 60 seconds."
     */
    fun sendOTP() {
        call(
            block = {
                delay(100)
                emailOrPhoneNumber?.let {
                    authRepository.signInWithOTP(
                        input = it,
                        signInType = signInType
                    )
                }
            }
        )
    }

    fun verifyOTP(otp: String) {
        call(
            block = {
                emailOrPhoneNumber?.let {
                    authRepository.verifyOTP(
                        input = it,
                        otp = otp,
                        signInType = signInType
                    )
                }
            }
        )
    }

    fun resendOTP() {
        call(
            block = {
                emailOrPhoneNumber?.let {
                    authRepository.resendOTP(
                        input = it,
                        signInType = signInType
                    )
                }
            }
        )
    }

    companion object {
        const val EMAIL_OR_PHONE_NUMBER_KEY = "emailOrPhoneNumber"
        const val INPUT_TYPE_KEY = "inputType"
        const val OTP_PATTERN = "(\\d{6})"
    }
}
