package com.zapplications.salonbooking.domain.repository

import com.zapplications.salonbooking.data.request.BookingAppointmentRequest
import com.zapplications.salonbooking.domain.model.BookingAppointmentUiModel
import com.zapplications.salonbooking.domain.model.BookingsUiModel

interface BookingRepository {
    suspend fun bookAppointment(bookingAppointmentRequest: BookingAppointmentRequest): BookingAppointmentUiModel?
    suspend fun getUserBookings(userId: String, status: String): List<BookingsUiModel>
}
