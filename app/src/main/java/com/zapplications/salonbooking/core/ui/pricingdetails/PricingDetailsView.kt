package com.zapplications.salonbooking.core.ui.pricingdetails

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.zapplications.salonbooking.databinding.LayoutPricingDetailsBinding

class BookingPricingDetailsView @JvmOverloads constructor(
    private val context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding = LayoutPricingDetailsBinding.inflate(LayoutInflater.from(context), this)

    fun initPricingDetails(bookingPricingDetail: BookingPricingDetail) = with(binding) {
        val total = bookingPricingDetail.prices.sumOf { it.price }

        bookingPricingDetail.title?.let {
            tvPricingDetailsTitle.text = it
        }

        initPricesLayout(bookingPricingDetail.prices)
        tvTotal.text = buildString { append("$"); append(total) }
    }

    private fun initPricesLayout(bookingPrices: List<BookingPrice>) {
        bookingPrices.forEach { price ->
            val item = PricingDetailItemView(context).apply {
                setPricingItem(pricingType = price.service, price = price.price.toString())
            }

            binding.llPricingDetails.addView(item)
        }
    }
}

data class BookingPricingDetail(
    val prices: List<BookingPrice>,
    val title: String? = null
)

data class BookingPrice(
    val service: String,
    val price: Double
)