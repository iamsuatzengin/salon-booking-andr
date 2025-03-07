package com.zapplications.salonbooking.core.ui.pricingdetails

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.zapplications.salonbooking.databinding.ItemPricingDetailsBinding

class PricingDetailItemView(
    context: Context,
) : LinearLayout(context) {
    private val binding =
        ItemPricingDetailsBinding.inflate(LayoutInflater.from(context), this, true)

    fun setPricingItem(
        pricingType: String,
        price: String
    ) = with(binding) {
        tvPricingType.text = pricingType
        tvPrice.text = buildString {
            append("$"); append(price)
        }
    }
}
