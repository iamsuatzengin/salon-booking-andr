package com.zapplications.salonbooking.core.ui.pricingdetails

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.zapplications.salonbooking.core.extensions.color
import com.zapplications.salonbooking.core.ui.pricingdetails.model.BookingPrice
import com.zapplications.salonbooking.databinding.ItemPricingDetailsBinding

class PricingDetailItemView(
    context: Context,
) : LinearLayout(context) {
    private val binding =
        ItemPricingDetailsBinding.inflate(LayoutInflater.from(context), this, true)

    fun setPricingItem(bookingPrice: BookingPrice) = with(binding) {
        tvPricingType.text = bookingPrice.service
        tvPrice.text = buildString {
            append("$"); append(bookingPrice.price)
        }

        bookingPrice.typeStringColor?.let {
            tvPricingType.setTextColor(resources.color(it))
        }

        bookingPrice.priceColor?.let {
            tvPrice.setTextColor(resources.color(it))
        }
    }
}
