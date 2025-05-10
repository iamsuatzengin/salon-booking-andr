package com.zapplications.salonbooking.data.datasource.remote

import com.zapplications.salonbooking.core.coroutineflow.apiCall
import com.zapplications.salonbooking.data.client.supabaseClient
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.OTP
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor() {
    fun isUserLoggedIn() = supabaseClient.auth.currentSessionOrNull() != null

    val sessionStatusStateFlow = supabaseClient.auth.sessionStatus

    suspend fun signInWithPhoneOTP(phoneNumber: String) = apiCall {
        supabaseClient.auth.signInWith(OTP) {
            phone = phoneNumber
        }
    }

    suspend fun verifyPhoneOTP(phoneNumber: String, otp: String) = apiCall {
        supabaseClient.auth.verifyPhoneOtp(
            type = OtpType.Phone.SMS,
            phone = phoneNumber,
            token = otp
        )
    }

    suspend fun resendPhoneOTP(phoneNumber: String) = apiCall {
        supabaseClient.auth.resendPhone(
            type = OtpType.Phone.SMS,
            phone = phoneNumber
        )
    }

    suspend fun signInWithEmailOTP(email: String) = apiCall(
        block = {
            supabaseClient.auth.signInWith(OTP) { this.email = email }
        }
    )

    suspend fun verifyEmailOTP(email: String, otp: String) = apiCall {
        supabaseClient.auth.verifyEmailOtp(
            type = OtpType.Email.EMAIL,
            email = email,
            token = otp
        )
    }

    suspend fun resendEmailOTP(email: String) = apiCall {
        supabaseClient.auth.resendEmail(
            type = OtpType.Email.SIGNUP,
            email = email
        )
    }
}
