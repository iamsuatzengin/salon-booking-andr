package com.zapplications.salonbooking.data.datasource.remote

import com.zapplications.salonbooking.core.coroutineflow.apiCall
import com.zapplications.salonbooking.data.client.SupabaseConstants
import com.zapplications.salonbooking.data.client.SupabaseConstants.TABLE_BOOKINGS
import com.zapplications.salonbooking.data.client.supabaseClient
import com.zapplications.salonbooking.data.request.BookingAppointmentRequest
import com.zapplications.salonbooking.data.response.BookingAppointmentApiModel
import com.zapplications.salonbooking.data.response.BookingsApiModel
import com.zapplications.salonbooking.domain.model.enums.BookingStatusType
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import javax.inject.Inject

class BookingRemoteDataSource @Inject constructor() {
    suspend fun bookAppointment(request: BookingAppointmentRequest) = apiCall {
        supabaseClient.postgrest.rpc(
            function = SupabaseConstants.FUNC_INSERT_AND_GET_BOOKINGS,
            parameters = request.jsonObject
        ).decodeSingleOrNull<BookingAppointmentApiModel>()
    }

    suspend fun getBookById(bookingId: String) = apiCall {
        supabaseClient.postgrest
            .from(TABLE_BOOKINGS)
            .select {
                filter { eq("id", bookingId) }
            }.decodeSingleOrNull<BookingAppointmentApiModel>()
    }

    suspend fun getUserBookings(userId: String, status: String): List<BookingsApiModel> = apiCall {
        val columns = Columns.raw(
            """
                    id,
                    customer_id,
                    selected_services,
                    booking_date,
                    booking_time,
                    final_amount,
                    payment_type,
                    status,
                    salon (
                      salon_name,
                      description,
                      address,
                      image_url
                    )
                """.trimIndent()
        )

        supabaseClient.postgrest
            .from(TABLE_BOOKINGS)
            .select(columns = columns) {
                filter {
                    eq("customer_id", userId)
                    eq("status", status)
                }
            }.decodeList<BookingsApiModel>()

    }

    suspend fun updateBooking(bookingId: String, statusType: BookingStatusType) = apiCall {
        supabaseClient.postgrest
            .from(TABLE_BOOKINGS)
            .update(
                update = {
                    set("status", statusType.name)
                }
            ) {
                filter { eq("id", bookingId) }
            }
    }
}
