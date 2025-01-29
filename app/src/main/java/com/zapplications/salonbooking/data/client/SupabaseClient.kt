package com.zapplications.salonbooking.data.client

import com.zapplications.salonbooking.BuildConfig
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.serializer.KotlinXSerializer
import kotlinx.serialization.json.Json

val supabaseClient = createSupabaseClient(
    supabaseUrl = BuildConfig.SUPABASE_URL,
    supabaseKey = BuildConfig.SUPABASE_API_KEY
) {
    defaultSerializer = KotlinXSerializer(Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    })

    install(Auth)
    install(Postgrest)
}
