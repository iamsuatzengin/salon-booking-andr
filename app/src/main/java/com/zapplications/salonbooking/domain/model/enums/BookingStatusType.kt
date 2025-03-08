package com.zapplications.salonbooking.domain.model.enums

import android.content.Context
import com.zapplications.salonbooking.R

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

        fun toResString(context: Context, statusType: BookingStatusType?): String = when (statusType) {
            UPCOMING -> context.getString(R.string.upcoming)
            COMPLETED -> context.getString(R.string.completed)
            CANCELLED -> context.getString(R.string.cancelled)
            null -> context.getString(R.string.upcoming)
        }
    }
}
