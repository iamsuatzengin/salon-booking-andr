package com.zapplications.salonbooking.core.extensions

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.BundleCompat

/**
 * Retrieves a Parcelable object from a Bundle, handling API level differences to avoid crashes.
 *
 * On Android API 33 (TIRAMISU), the standard `Bundle.getParcelable(String, Class)` method can cause crashes.
 * This function provides a workaround by using `BundleCompat.getParcelable(Bundle, String, Class)` on API 33.
 * For API 34 (UPSIDE_DOWN_CAKE) and above, `Bundle.getParcelable(String, Class)` can be used safely again.
 * For API levels below 33, `Bundle.getParcelable<T>(String)` is used.
 *
 * @param key The key associated with the Parcelable object in the Bundle.
 * @return The Parcelable object of type T, or null if no object is associated with the key or if the object is null.
 * @throws ClassCastException if the retrieved object cannot be cast to type T.
 * @throws NullPointerException if the Bundle is null.
 */
inline fun <reified T : Parcelable?> Bundle.parcelable(key: String): T? {
    return if (Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU)  {
        BundleCompat.getParcelable(this, key, T::class.java)
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
        getParcelable(key, T::class.java)
    } else {
        getParcelable<T>(key)
    }
}
