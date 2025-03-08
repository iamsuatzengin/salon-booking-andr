package com.zapplications.salonbooking.data.datasource.remote

import android.util.Log
import com.zapplications.salonbooking.data.client.SupabaseConstants
import com.zapplications.salonbooking.data.client.supabaseClient
import com.zapplications.salonbooking.data.request.BookingAppointmentRequest
import com.zapplications.salonbooking.data.response.BookingAppointmentApiModel
import io.github.jan.supabase.postgrest.postgrest
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
}
