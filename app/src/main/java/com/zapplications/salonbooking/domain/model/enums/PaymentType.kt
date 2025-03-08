package com.zapplications.salonbooking.domain.model.enums

enum class PaymentType {
    ONLINE,
    AT_SALON;

    companion object {
        fun fromString(value: String?): PaymentType = when (value) {
            "ONLINE" -> ONLINE
            "AT_SALON" -> AT_SALON
            else -> AT_SALON
        }
    }
}
