package com.zapplications.salonbooking.data.datasource.remote

import com.zapplications.salonbooking.data.client.SupabaseConstants
import com.zapplications.salonbooking.data.client.supabaseClient
import com.zapplications.salonbooking.data.response.SalonApiModel
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
                    put("p_salon_id", salonId)
                }
            ).decodeAsOrNull<SalonApiModel>()
        }.getOrNull()
    }
}
