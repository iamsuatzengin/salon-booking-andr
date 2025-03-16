package com.zapplications.salonbooking.ui.bookings.adapter

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.extensions.loadImage
import com.zapplications.salonbooking.databinding.ItemBookingsListBinding
import com.zapplications.salonbooking.domain.model.BookingsUiModel

class BookingsViewHolder(
    private val binding: ItemBookingsListBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: BookingsUiModel, clickHandler: BookingItemClickHandler) = with(binding) {
        tvBookDateTime.text = root.context.getString(
            R.string.text_date_time,
            item.bookingDate,
            item.bookingTime,
        )

        item.salon?.imageUrl?.let {
            root.context.loadImage(
                url = it,
                target = ivSalonImage,
                radius = root.context.resources.getDimension(R.dimen.corner_radius_default).toInt()
            )
        }

        tvSalonNameTitle.text = item.salon?.salonName
        tvSalonLocationString.text = item.salon?.address
        tvServices.text = root.context.getString(
            R.string.text_selected_services,
            item.selectedServices.joinToString(",") { it.serviceName }
        )

        btnCancelBooking.isVisible = item.isUpcoming
        btnViewReceipt.isVisible = !item.isCancelled
        tvCancelledTitle.isVisible = item.isCancelled

        btnViewReceipt.setOnClickListener { clickHandler.onViewReceiptClick(item) }
        btnCancelBooking.setOnClickListener { clickHandler.onCancelBookingClick(item) }
    }
}
