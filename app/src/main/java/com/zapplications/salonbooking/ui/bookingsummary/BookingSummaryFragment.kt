package com.zapplications.salonbooking.ui.bookingsummary

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.extensions.TEN
import com.zapplications.salonbooking.core.extensions.ZERO
import com.zapplications.salonbooking.core.extensions.loadImage
import com.zapplications.salonbooking.core.extensions.toast
import com.zapplications.salonbooking.core.ui.pricingdetails.model.BookingPrice
import com.zapplications.salonbooking.core.ui.pricingdetails.model.BookingPricingDetail
import com.zapplications.salonbooking.core.viewBinding
import com.zapplications.salonbooking.databinding.FragmentBookingSummaryBinding
import com.zapplications.salonbooking.domain.model.SelectedServices
import com.zapplications.salonbooking.domain.model.ServiceUiModel
import com.zapplications.salonbooking.domain.model.enums.PaymentType
import com.zapplications.salonbooking.ui.shared.AppointmentSharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookingSummaryFragment : Fragment(R.layout.fragment_booking_summary) {
    private val binding by viewBinding(FragmentBookingSummaryBinding::bind)
    private val sharedViewModel: AppointmentSharedViewModel by navGraphViewModels(R.id.home_graph)
    private val viewModel: BookingSummaryViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        collectData()
    }

    private fun collectData() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect { uiState ->
                        if (uiState.isLoading) {
                            toast("Loading")
                        }
                    }
                }

                launch {
                    viewModel.uiEvent.collect { uiEvent ->
                        when (uiEvent) {
                            is BookingSummaryUiEvent.BookingAppointmentSuccessFull -> {
                                // TODO navigate to receipt screen with booking result [uiEvent.bookingAppointmentUiModel]
                            }

                            is BookingSummaryUiEvent.ShowError -> {
                                toast(message = uiEvent.message)
                            }
                        }
                    }
                }
            }
        }
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
            toast("Please, select payment type!")
            return
        }

        bookAppointment()
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

    private fun bookAppointment() {
        val discountAmount = ZERO
        val totalAmount = binding.layoutBookingPricingDetails.totalAmount
        val finalAmount = totalAmount - discountAmount
        val paymentType =
            if (binding.rbPayOnlineNow.isChecked) PaymentType.ONLINE else PaymentType.AT_SALON

        val selectedServices = sharedViewModel.selectedServices?.map {
            SelectedServices(
                serviceName = it.serviceName,
                servicePrice = it.customPrice
            )
        }.orEmpty()

        val request = viewModel.createRequest(
            salonId = sharedViewModel.salon?.id.orEmpty(),
            stylistId = sharedViewModel.selectedStylist?.id.orEmpty(),
            bookingDate = sharedViewModel.selectedDate?.formattedDate.orEmpty(),
            bookingTime = sharedViewModel.selectedTime?.time.toString(),
            totalAmount = totalAmount,
            discountAmount = discountAmount,
            finalAmount = finalAmount,
            paymentType = paymentType,
            selectedServices = selectedServices
        )

        viewModel.bookAppointment(request)
    }
}