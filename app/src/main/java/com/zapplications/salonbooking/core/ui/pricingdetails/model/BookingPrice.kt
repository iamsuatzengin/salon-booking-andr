package com.zapplications.salonbooking.core.ui.pricingdetails.model

import androidx.annotation.ColorRes

data class BookingPrice(
    val service: String,
    val price: Double,
    @ColorRes val typeStringColor: Int? = null,
    @ColorRes val priceColor: Int? = null
)
