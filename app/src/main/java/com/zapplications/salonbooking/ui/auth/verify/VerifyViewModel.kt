package com.zapplications.salonbooking.ui.auth.verify

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zapplications.salonbooking.domain.model.SignInType
import com.zapplications.salonbooking.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val authRepository: AuthRepository,
) : ViewModel() {

    val authenticationState = authRepository.sessionStatusStateFlow

    val emailOrPhoneNumber = savedStateHandle.get<String>(EMAIL_OR_PHONE_NUMBER_KEY)
    val signInType = savedStateHandle.get<SignInType>(INPUT_TYPE_KEY) ?: SignInType.Invalid

    var otpCode: String? = null

    /**
     *TODO: check request OTP delay
     * "AuthRestException: For security purposes, you can only request this after 60 seconds."
     */
    fun sendOTP() {
        viewModelScope.launch {
            delay(100)
            emailOrPhoneNumber?.let {
                authRepository.signInWithOTP(
                    input = it,
                    signInType = signInType
                )
            }
        }
    }

    fun verifyOTP(otp: String) {
        viewModelScope.launch {
            emailOrPhoneNumber?.let {
                authRepository.verifyOTP(
                    input = it,
                    otp = otp,
                    signInType = signInType
                )
            }
        }
    }

    fun resendOTP() {
        viewModelScope.launch {
            emailOrPhoneNumber?.let {
                authRepository.resendOTP(
                    input = it,
                    signInType = signInType
                )
            }
        }
    }

    companion object {
        const val EMAIL_OR_PHONE_NUMBER_KEY = "emailOrPhoneNumber"
        const val INPUT_TYPE_KEY = "inputType"
    }
}
