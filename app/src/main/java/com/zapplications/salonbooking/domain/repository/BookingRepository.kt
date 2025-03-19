package com.zapplications.salonbooking.domain.repository

import com.zapplications.salonbooking.data.request.BookingAppointmentRequest
import com.zapplications.salonbooking.domain.model.BookingAppointmentUiModel
import com.zapplications.salonbooking.domain.model.BookingsUiModel
import com.zapplications.salonbooking.domain.model.enums.BookingStatusType
import io.github.jan.supabase.postgrest.result.PostgrestResult

interface BookingRepository {
    suspend fun bookAppointment(bookingAppointmentRequest: BookingAppointmentRequest): BookingAppointmentUiModel?
    suspend fun getBookById(bookingId: String): BookingAppointmentUiModel?
    suspend fun getUserBookings(userId: String, status: String): List<BookingsUiModel>
    suspend fun updateBooking(bookingId: String, statusType: BookingStatusType): PostgrestResult?
}
