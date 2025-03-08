package com.zapplications.salonbooking.core.ui.pricingdetails.model

data class BookingPricingDetail(
    val prices: List<BookingPrice>,
    val title: String? = null
)
