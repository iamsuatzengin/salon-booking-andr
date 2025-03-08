package com.zapplications.salonbooking.data.repository

import com.zapplications.salonbooking.data.datasource.remote.BookingRemoteDataSource
import com.zapplications.salonbooking.data.request.BookingAppointmentRequest
import com.zapplications.salonbooking.domain.model.BookingAppointmentUiModel
import com.zapplications.salonbooking.domain.model.toUiModel
import com.zapplications.salonbooking.domain.repository.BookingRepository
import javax.inject.Inject

class BookingRepositoryImpl @Inject constructor(
    private val dataSource: BookingRemoteDataSource,
) : BookingRepository {
    override suspend fun bookAppointment(bookingAppointmentRequest: BookingAppointmentRequest): BookingAppointmentUiModel? {
        return dataSource.bookAppointment(bookingAppointmentRequest)?.toUiModel()
    }
}
