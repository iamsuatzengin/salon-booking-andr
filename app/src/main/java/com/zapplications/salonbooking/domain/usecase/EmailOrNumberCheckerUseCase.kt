package com.zapplications.salonbooking.domain.usecase

import com.zapplications.salonbooking.domain.model.SignInType
import javax.inject.Inject

class EmailOrNumberCheckerUseCase @Inject constructor() {
    operator fun invoke(input: String): SignInType {
        val emailRegex = Regex(REGEX_EMAIL_PATTERN)
        val phoneRegex = Regex(REGEX_PHONE_PATTERN)

        return when {
            emailRegex.matches(input) -> SignInType.Email
            phoneRegex.matches(input) -> SignInType.Number
            else -> SignInType.Invalid
        }
    }

    companion object {
        const val REGEX_EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
        const val REGEX_PHONE_PATTERN = "^\\+?\\d{10,15}$"
    }
}
