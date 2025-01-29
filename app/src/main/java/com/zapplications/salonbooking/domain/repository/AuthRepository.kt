package com.zapplications.salonbooking.domain.repository

import com.zapplications.salonbooking.domain.model.SignInType
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    val sessionStatusStateFlow: StateFlow<SessionStatus>
    fun isUserLoggedIn(): Boolean
    suspend fun signInWithOTP(input: String, signInType: SignInType)
    suspend fun verifyOTP(input: String, otp: String, signInType: SignInType)
    suspend fun resendOTP(input: String, signInType: SignInType)
}
