package com.zapplications.salonbooking.data.repository

import com.zapplications.salonbooking.data.datasource.remote.AuthRemoteDataSource
import com.zapplications.salonbooking.domain.model.SignInType
import com.zapplications.salonbooking.domain.repository.AuthRepository
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * **Current Implementation Note:**
 *
 *TODO:
 * The current implementation utilizes `if/else` conditions to handle different [SignInType]s.
 * This might be refactored to a more scalable and maintainable solution in the future, potentially using a strategy pattern or a map-based approach.
 */
class AuthRepositoryImpl @Inject constructor(
    private val dataSource: AuthRemoteDataSource
) : AuthRepository {
    override val sessionStatusStateFlow: StateFlow<SessionStatus>
        get() = dataSource.sessionStatusStateFlow

    override fun isUserLoggedIn(): Boolean = dataSource.isUserLoggedIn()

    override suspend fun signInWithOTP(input: String, signInType: SignInType) {
        if (signInType == SignInType.Email) {
            dataSource.signInWithEmailOTP(input)
        } else {
            dataSource.signInWithPhoneOTP(input)
        }
    }

    override suspend fun verifyOTP(input: String, otp: String, signInType: SignInType) {
        if (signInType == SignInType.Email) {
            dataSource.verifyEmailOTP(input, otp)
        } else {
            dataSource.verifyPhoneOTP(input, otp)
        }
    }

    override suspend fun resendOTP(input: String, signInType: SignInType) {
        if (signInType == SignInType.Email) {
            dataSource.resendEmailOTP(input)
        } else {
            dataSource.resendPhoneOTP(input)
        }
    }
}
