package com.zapplications.salonbooking.core

import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.provider.Settings
import com.zapplications.salonbooking.core.extensions.ONE
import com.zapplications.salonbooking.core.extensions.ZERO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object LocationUtil {
    suspend fun getFromLocation(
        context: Context,
        longitude: Double,
        latitude: Double,
    ): Address? =
        withContext(Dispatchers.IO) {
            val geocoder = Geocoder(context)

            suspendCoroutine { continuation ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    geocoder.getFromLocation(
                        latitude, longitude, ONE
                    ) { addresses ->
                        continuation.resume(addresses.getOrNull(ZERO))
                    }
                } else {
                    geocoder.getFromLocation(
                        latitude, longitude, ONE
                    )?.let { addresses ->
                        continuation.resume(addresses.getOrNull(ZERO))
                    }
                }
            }
        }

    fun openLocationSettings(context: Context) {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        context.startActivity(intent)
    }

    fun getLocationPermission() = arrayOf(
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )
}
