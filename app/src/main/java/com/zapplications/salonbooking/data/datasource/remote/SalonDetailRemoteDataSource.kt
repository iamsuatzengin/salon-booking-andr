package com.zapplications.salonbooking.data.datasource.remote

import android.util.Log
import com.zapplications.salonbooking.data.client.SupabaseConstants
import com.zapplications.salonbooking.data.client.supabaseClient
import com.zapplications.salonbooking.data.request.StylistAvailabilityRequest
import com.zapplications.salonbooking.data.response.SalonApiModel
import com.zapplications.salonbooking.data.response.StylistApiModel
import com.zapplications.salonbooking.data.response.StylistAvailabilityApiModel
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import javax.inject.Inject

class SalonDetailRemoteDataSource @Inject constructor() {
    suspend fun getSalonDetail(salonId: String): SalonApiModel? = withContext(Dispatchers.IO) {
        runCatching {
            supabaseClient.postgrest.rpc(
                SupabaseConstants.FUNC_GET_SALON_DETAILS,
                parameters = buildJsonObject {
                    put(SupabaseConstants.FUNC_PARAM_SALON_ID, salonId)
                }
            ).decodeAsOrNull<SalonApiModel>()
        }.getOrNull()
    }

    suspend fun getStylistsBySalonId(salonId: String): List<StylistApiModel>? =
        withContext(Dispatchers.IO) {
            runCatching {
                supabaseClient.postgrest.from(SupabaseConstants.TABLE_STYLIST)
                    .select {
                        filter { eq(SupabaseConstants.FILTER_SALON_ID, salonId) }
                    }
                    .decodeList<StylistApiModel>()
            }.getOrElse {
                Log.e("SalonDetailRemoteDataSource", "getStylistsBySalonId: $it")
                null
            }
        }

    suspend fun getStylistAvailability(
        stylistId: String,
        date: String
    ) : StylistAvailabilityApiModel? = withContext(Dispatchers.IO) {
        runCatching {
            val request = StylistAvailabilityRequest(stylistId, date).jsonObject
            supabaseClient.postgrest.rpc(
                function = SupabaseConstants.FUNC_GET_STYLIST_AVAILABILITY,
                parameters = request
            ).decodeList<StylistAvailabilityApiModel>().lastOrNull()
        }.getOrElse {
            Log.e("SalonDetailRemoteDataSource", "getStylistAvailability: $it")
            null
        }
    }
}
