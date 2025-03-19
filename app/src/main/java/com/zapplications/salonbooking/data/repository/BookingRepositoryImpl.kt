package com.zapplications.salonbooking.data.repository

import com.zapplications.salonbooking.data.datasource.remote.BookingRemoteDataSource
import com.zapplications.salonbooking.data.request.BookingAppointmentRequest
import com.zapplications.salonbooking.domain.model.BookingAppointmentUiModel
import com.zapplications.salonbooking.domain.model.BookingsUiModel
import com.zapplications.salonbooking.domain.model.enums.BookingStatusType
import com.zapplications.salonbooking.domain.model.toUiModel
import com.zapplications.salonbooking.domain.repository.BookingRepository
import io.github.jan.supabase.postgrest.result.PostgrestResult
import javax.inject.Inject

class BookingRepositoryImpl @Inject constructor(
    private val dataSource: BookingRemoteDataSource,
) : BookingRepository {
    override suspend fun bookAppointment(bookingAppointmentRequest: BookingAppointmentRequest): BookingAppointmentUiModel? {
        return dataSource.bookAppointment(bookingAppointmentRequest)?.toUiModel()
    }

    override suspend fun getBookById(bookingId: String): BookingAppointmentUiModel? {
        return dataSource.getBookById(bookingId).getOrNull()?.toUiModel()
    }

    override suspend fun getUserBookings(userId: String, status: String): List<BookingsUiModel> {
        return dataSource.getUserBookings(userId, status).toUiModel()
    }

    override suspend fun updateBooking(
        bookingId: String,
        statusType: BookingStatusType,
    ): PostgrestResult? = dataSource.updateBooking(bookingId = bookingId, statusType = statusType)
}
