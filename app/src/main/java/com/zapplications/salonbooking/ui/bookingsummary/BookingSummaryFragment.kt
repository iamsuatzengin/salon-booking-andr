package com.zapplications.salonbooking.ui.bookingsummary

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.extensions.TEN
import com.zapplications.salonbooking.core.extensions.ZERO
import com.zapplications.salonbooking.core.extensions.loadImage
import com.zapplications.salonbooking.core.extensions.toast
import com.zapplications.salonbooking.core.ui.pricingdetails.BookingPrice
import com.zapplications.salonbooking.core.ui.pricingdetails.BookingPricingDetail
import com.zapplications.salonbooking.core.viewBinding
import com.zapplications.salonbooking.databinding.FragmentBookingSummaryBinding
import com.zapplications.salonbooking.domain.model.ServiceUiModel
import com.zapplications.salonbooking.ui.shared.AppointmentSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingSummaryFragment : Fragment(R.layout.fragment_booking_summary) {
    private val binding by viewBinding(FragmentBookingSummaryBinding::bind)
    private val sharedViewModel: AppointmentSharedViewModel by navGraphViewModels(R.id.home_graph)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        initSalonCard()
        binding.tvBookingDate.text = buildString {
            append(sharedViewModel.selectedDate?.dayOfWeekText)
            append(" , ")
            append(sharedViewModel.selectedDate?.month)
            append(" ")
            append(sharedViewModel.selectedDate?.dayOfMonthNumber)
            append(" at ")
            append(sharedViewModel.selectedTime?.time)
        }

        binding.tvStylist.text = buildString {
            append(sharedViewModel.selectedStylist?.fullName)
            append(" - ")
            append(
                sharedViewModel.salon?.services?.firstOrNull()?.customDuration
                    ?: sharedViewModel.salon?.services?.firstOrNull()?.defaultDuration
            )
        }
        sharedViewModel.selectedServices?.let { initPricingDetails(it) }

        handleClickEvents()

    }

    private fun initSalonCard() = with(binding.layoutSalonCard) {
        sharedViewModel.salon?.apply {
            tvSalonName.text = salonName

            loadImage(
                url = imageUrl,
                target = ivSalonImage,
                radius = TEN
            )
            tvSalonLocationString.text = address
            tvRating.text = "$rating ($reviewerCount)"
        }
    }

    private fun initPricingDetails(services: List<ServiceUiModel>) = with(binding) {
        val prices = services.map { service ->
            BookingPrice(
                service = service.serviceName,
                price = if (service.customPrice > ZERO) service.customPrice else service.defaultPrice
            )
        }
        val bookingPricingDetail = BookingPricingDetail(prices = prices)
        layoutBookingPricingDetails.initPricingDetails(bookingPricingDetail)
    }

    private fun handleProceedButtonClick() {
        if (!binding.rbPayOnlineNow.isChecked && !binding.rbPayAtSalon.isChecked) {
            toast("Ödeme türünü seç")
            return
        }

        toast("Proceed button clicked")
    }

    private fun handleClickEvents() = with(binding) {

        ivBackIcon.setOnClickListener { findNavController().navigateUp() }
        tvPayNowLabel.setOnClickListener { binding.rbPayOnlineNow.isChecked = true }
        tvPayAtSalonLabel.setOnClickListener { binding.rbPayAtSalon.isChecked = true }

        rbPayOnlineNow.setOnCheckedChangeListener { _, isChecked ->
            rbPayAtSalon.isChecked = !isChecked
        }

        rbPayAtSalon.setOnCheckedChangeListener { _, isChecked ->
            rbPayOnlineNow.isChecked = !isChecked
        }

        btnProceed.setOnClickListener { handleProceedButtonClick() }
    }
}