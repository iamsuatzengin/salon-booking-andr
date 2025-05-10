package com.zapplications.salonbooking.core.coroutineflow

import io.github.jan.supabase.auth.exception.AuthRestException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> apiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    block: suspend () -> T,
) = withContext(dispatcher) {
    runCatching { block() }.getOrElse { throwable ->
        when (throwable) {
            is AuthRestException -> {
                throw AppException(ApiErrorModel.authenticationCommonError)
            }

            else -> {
                throw AppException(ApiErrorModel(
                    title = "Something went wrong",
                    description = throwable.message
                        ?: "Something went wrong. We are working on it. Please try again later."
                ))
            }
        }
    }
}
