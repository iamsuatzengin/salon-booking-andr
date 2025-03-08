package com.zapplications.salonbooking.domain.model.enums

enum class BookingStatusType {
    UPCOMING,
    COMPLETED,
    CANCELLED;

    companion object {
        fun fromString(value: String?): BookingStatusType = when (value) {
            "UPCOMING" -> UPCOMING
            "COMPLETED" -> COMPLETED
            "CANCELLED" -> CANCELLED
            else -> UPCOMING
        }
    }
}
