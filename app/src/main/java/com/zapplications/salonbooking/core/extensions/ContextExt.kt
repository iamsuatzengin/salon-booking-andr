package com.zapplications.salonbooking.core.extensions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.LocationManager
import android.os.Build
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

fun Int.dpToPx(context: Context): Int {
    return (this * context.resources.displayMetrics.density).toInt()
}

fun Resources.drawable(@DrawableRes resId: Int) = ResourcesCompat.getDrawable(this, resId, null)
fun Resources.color(@ColorRes resId: Int) = ResourcesCompat.getColor(this, resId, null)

fun Context.hideKeyboard(input: AppCompatEditText) {
    val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    manager.hideSoftInputFromWindow(input.windowToken, 0)
}

fun Context.showKeyboard(input: AppCompatEditText) {
    val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    manager.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT)
}

fun Activity.hideKeyboard() {
    val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    manager.hideSoftInputFromWindow(window.decorView.windowToken, 0)
}

fun Fragment.hideKeyboard() {
    val manager =
        requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    manager.hideSoftInputFromWindow(requireView().windowToken, 0)
}

fun Context.checkNotificationPermission(): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val perm = ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)

        return perm == PackageManager.PERMISSION_GRANTED
    }

    return true
}

fun Context.checkLocationPermission(): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}

fun Context.checkLocationProviderEnabled(): Boolean {
    val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
}

fun Context.loadImage(
    url: String,
    target: ImageView,
    radius: Int,
) {
    Glide.with(this)
        .load(url)
        .transform(CenterCrop(), RoundedCorners(radius))
        .into(target)
}
