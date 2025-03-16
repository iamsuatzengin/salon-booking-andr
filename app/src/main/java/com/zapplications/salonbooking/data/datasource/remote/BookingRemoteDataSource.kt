package com.zapplications.salonbooking.data.datasource.remote

import android.util.Log
import com.zapplications.salonbooking.data.client.SupabaseConstants
import com.zapplications.salonbooking.data.client.supabaseClient
import com.zapplications.salonbooking.data.request.BookingAppointmentRequest
import com.zapplications.salonbooking.data.response.BookingAppointmentApiModel
import com.zapplications.salonbooking.data.response.BookingsApiModel
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookingRemoteDataSource @Inject constructor() {
    suspend fun bookAppointment(request: BookingAppointmentRequest) = withContext(Dispatchers.IO) {
        runCatching {
            supabaseClient.postgrest.rpc(
                function = SupabaseConstants.FUNC_INSERT_AND_GET_BOOKINGS,
                parameters = request.jsonObject
            ).decodeSingleOrNull<BookingAppointmentApiModel>()
        }.getOrElse {
            Log.e("BookingRemoteDataSource", "bookAppointment: $it")
            null
        }
    }

    suspend fun getUserBookings(userId: String, status: String): List<BookingsApiModel> =
        withContext(Dispatchers.IO) {
            runCatching {
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
                    .from("bookings")
                    .select(columns = columns) {
                        filter {
                            eq("customer_id", userId)
                            eq("status", status)
                        }
                    }.decodeList<BookingsApiModel>()
            }.getOrElse {
                Log.e("BookingsRemoteDataSource", "getUserBookings: $it")
                emptyList()
            }
        }
}
