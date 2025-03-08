package com.zapplications.salonbooking.domain.model.enums

import android.content.Context
import com.zapplications.salonbooking.R

enum class PaymentType {
    ONLINE,
    AT_SALON;

    companion object {
        fun fromString(value: String?): PaymentType = when (value) {
            "ONLINE" -> ONLINE
            "AT_SALON" -> AT_SALON
            else -> AT_SALON
        }

        fun toResString(context: Context, paymentType: PaymentType?) = when (paymentType) {
            ONLINE -> context.getString(R.string.online)
            AT_SALON -> context.getString(R.string.at_salon)
            else -> context.getString(R.string.at_salon)
        }
    }
}
