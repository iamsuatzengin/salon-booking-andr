package com.zapplications.salonbooking.core.extensions

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.view.inputmethod.InputMethodManager
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment

fun Resources.drawable(@DrawableRes resId: Int) = ResourcesCompat.getDrawable(this, resId, null)

fun Context.hideKeyboard(input: AppCompatEditText) {
    val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    manager.hideSoftInputFromWindow(input.windowToken, 0)
}

fun Activity.hideKeyboard() {
    val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    manager.hideSoftInputFromWindow(window.decorView.windowToken, 0)
}

fun Fragment.hideKeyboard() {
    val manager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    manager.hideSoftInputFromWindow(requireView().windowToken, 0)
}
