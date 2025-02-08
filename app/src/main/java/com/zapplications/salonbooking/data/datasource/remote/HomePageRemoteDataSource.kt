package com.zapplications.salonbooking.data.datasource.remote

import com.zapplications.salonbooking.core.extensions.ZERO
import com.zapplications.salonbooking.data.response.HomePageApiModel
import com.zapplications.salonbooking.data.client.SupabaseConstants
import com.zapplications.salonbooking.data.client.supabaseClient
import com.zapplications.salonbooking.data.request.HomePageDataRequest
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomePageRemoteDataSource @Inject constructor() {
    suspend fun getAllHomePageData(): HomePageApiModel? = withContext(Dispatchers.IO) {
        runCatching {
            supabaseClient.postgrest
                .rpc(SupabaseConstants.FUNC_GET_ALL_HOME_PAGE_DATA)
                .decodeList<HomePageApiModel>()
                .getOrNull(ZERO)
        }.getOrNull()
    }

    suspend fun getHomePageDataByLocation(
        longitude: Double,
        latitude: Double
    ): HomePageApiModel? = withContext(Dispatchers.IO) {
        runCatching {
            val request = HomePageDataRequest(
                latitude = latitude,
                longitude = longitude
            ).jsonObject

            supabaseClient.postgrest
                .rpc(
                    function = SupabaseConstants.FUNC_GET_HOME_PAGE_DATA_BY_LOCATION,
                    parameters = request
                )
                .decodeList<HomePageApiModel>()
                .getOrNull(ZERO)
        }.getOrNull()
    }
}
