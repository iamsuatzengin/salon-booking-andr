package com.zapplications.salonbooking.core.ui.pricingdetails

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.zapplications.salonbooking.core.ui.pricingdetails.model.BookingPrice
import com.zapplications.salonbooking.core.ui.pricingdetails.model.BookingPricingDetail
import com.zapplications.salonbooking.databinding.LayoutPricingDetailsBinding

class BookingPricingDetailsView @JvmOverloads constructor(
    private val context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding = LayoutPricingDetailsBinding.inflate(LayoutInflater.from(context), this)

    var totalAmount: Double = 0.0
        private set

    fun initPricingDetails(bookingPricingDetail: BookingPricingDetail) = with(binding) {
        val total = bookingPricingDetail.prices.sumOf { it.price }

        bookingPricingDetail.title?.let {
            tvPricingDetailsTitle.text = it
        }

        initPricesLayout(bookingPricingDetail.prices)
        totalAmount = total
        tvTotal.text = buildString { append("$"); append(total) }
    }

    private fun initPricesLayout(bookingPrices: List<BookingPrice>) {
        bookingPrices.forEach { price ->
            val item = PricingDetailItemView(context).apply {
                setPricingItem(price)
            }

            binding.llPricingDetails.addView(item)
        }
    }
}



