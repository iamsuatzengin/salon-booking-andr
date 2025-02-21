package com.zapplications.salonbooking.core.extensions

import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

fun Fragment.loadImage(
    url: String,
    target: ImageView,
    radius: Int,
) {
    Glide.with(this)
        .load(url)
        .transform(CenterCrop(), RoundedCorners(radius))
        .into(target)
}
