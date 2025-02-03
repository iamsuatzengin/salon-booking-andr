package com.zapplications.salonbooking.core

import android.app.PendingIntent
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.util.Log
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.zapplications.salonbooking.core.extensions.ONE
import com.zapplications.salonbooking.core.extensions.ZERO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object LocationUtil {
    const val INTERVAL_IN_MILLIS: Long = 3000

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

    fun enableLocation(
        context: Context,
        onError: (PendingIntent) -> Unit
    ) {
        val locationRequest = LocationRequest.Builder(INTERVAL_IN_MILLIS)
        val locationSettingsRequest = LocationSettingsRequest.Builder().addLocationRequest(
            locationRequest.build()
        )

        val task = LocationServices.getSettingsClient(context).checkLocationSettings(
            locationSettingsRequest.build()
        )

        task.addOnFailureListener { exception ->
            val statusCode = (exception as ResolvableApiException).statusCode

            if (statusCode == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                runCatching {
                    onError(exception.resolution)
                }.onFailure {
                    Log.i("Location - error start res.", "$it")
                }
            }
        }
    }
}
