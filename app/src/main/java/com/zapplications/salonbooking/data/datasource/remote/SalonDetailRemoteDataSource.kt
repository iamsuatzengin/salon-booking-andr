package com.zapplications.salonbooking.data.datasource.remote

import com.zapplications.salonbooking.core.coroutineflow.apiCall
import com.zapplications.salonbooking.data.client.SupabaseConstants
import com.zapplications.salonbooking.data.client.supabaseClient
import com.zapplications.salonbooking.data.request.StylistAvailabilityRequest
import com.zapplications.salonbooking.data.response.SalonApiModel
import com.zapplications.salonbooking.data.response.StylistApiModel
import com.zapplications.salonbooking.data.response.StylistAvailabilityApiModel
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import javax.inject.Inject

class SalonDetailRemoteDataSource @Inject constructor() {
    suspend fun getSalonDetail(salonId: String): SalonApiModel? = apiCall {
        supabaseClient.postgrest.rpc(
            SupabaseConstants.FUNC_GET_SALON_DETAILS,
            parameters = buildJsonObject {
                put(SupabaseConstants.FUNC_PARAM_SALON_ID, salonId)
            }
        ).decodeAsOrNull<SalonApiModel>()
    }

    suspend fun getStylistsBySalonId(salonId: String): List<StylistApiModel> = apiCall {
        supabaseClient.postgrest.from(SupabaseConstants.TABLE_STYLIST)
            .select {
                filter { eq(SupabaseConstants.FILTER_SALON_ID, salonId) }
            }
            .decodeList<StylistApiModel>()
    }

    suspend fun getStylistAvailability(
        stylistId: String,
        date: String,
    ): StylistAvailabilityApiModel? = apiCall {
        val request = StylistAvailabilityRequest(stylistId, date).jsonObject
        supabaseClient.postgrest.rpc(
            function = SupabaseConstants.FUNC_GET_STYLIST_AVAILABILITY,
            parameters = request
        ).decodeList<StylistAvailabilityApiModel>().lastOrNull()
    }
}
