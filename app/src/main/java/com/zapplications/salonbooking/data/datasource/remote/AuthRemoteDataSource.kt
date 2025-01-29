package com.zapplications.salonbooking.data.datasource.remote

import com.zapplications.salonbooking.data.client.supabaseClient
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.OTP
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor() {
    fun isUserLoggedIn() = supabaseClient.auth.currentSessionOrNull() != null

    val sessionStatusStateFlow = supabaseClient.auth.sessionStatus

    suspend fun signInWithPhoneOTP(phoneNumber: String) = withContext(Dispatchers.IO) {
        supabaseClient.auth.signInWith(OTP) {
            phone = phoneNumber
        }
    }

    suspend fun verifyPhoneOTP(phoneNumber: String, otp: String) = withContext(Dispatchers.IO) {
        supabaseClient.auth.verifyPhoneOtp(
            type = OtpType.Phone.SMS,
            phone = phoneNumber,
            token = otp
        )
    }

    suspend fun resendPhoneOTP(phoneNumber: String) = withContext(Dispatchers.IO) {
        supabaseClient.auth.resendPhone(
            type = OtpType.Phone.SMS,
            phone = phoneNumber
        )
    }

    suspend fun signInWithEmailOTP(email: String) = withContext(Dispatchers.IO) {
        supabaseClient.auth.signInWith(OTP) {
            this.email = email
        }
    }

    suspend fun verifyEmailOTP(email: String, otp: String) = withContext(Dispatchers.IO) {
        supabaseClient.auth.verifyEmailOtp(
            type = OtpType.Email.EMAIL,
            email = email,
            token = otp
        )
    }

    suspend fun resendEmailOTP(email: String) = withContext(Dispatchers.IO) {
        supabaseClient.auth.resendEmail(
            type = OtpType.Email.SIGNUP,
            email = email
        )
    }
}
