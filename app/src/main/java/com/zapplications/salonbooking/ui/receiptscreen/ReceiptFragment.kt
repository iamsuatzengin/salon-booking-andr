package com.zapplications.salonbooking.ui.receiptscreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.extensions.toast
import com.zapplications.salonbooking.core.ui.BaseFragment
import com.zapplications.salonbooking.core.ui.ShowError
import com.zapplications.salonbooking.core.ui.applyinset.InsetSides
import com.zapplications.salonbooking.core.ui.applyinset.applySystemBarInsetsAsPadding
import com.zapplications.salonbooking.core.ui.pricingdetails.PricingDetailItemView
import com.zapplications.salonbooking.core.ui.pricingdetails.model.BookingPrice
import com.zapplications.salonbooking.core.viewBinding
import com.zapplications.salonbooking.databinding.FragmentReceiptBinding
import com.zapplications.salonbooking.domain.model.enums.BookingStatusType
import com.zapplications.salonbooking.domain.model.enums.PaymentType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReceiptFragment : BaseFragment<ReceiptViewModel>(R.layout.fragment_receipt) {
    private val binding by viewBinding(FragmentReceiptBinding::bind)
    override val viewModel: ReceiptViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() = with(binding) {
        binding.clContainer.applySystemBarInsetsAsPadding(InsetSides(top = true, bottom = true))

        val uiModel = viewModel.bookingAppointmentUiModel

        viewModel.generateQrCodeBitmap()

        tvSalonName.text = uiModel?.salonName
        tvBookingDate.text = uiModel?.bookingDate
        tvBookingTime.text = uiModel?.bookingTime
        tvStylist.text = uiModel?.stylistName
        tvStatus.text = BookingStatusType.toResString(requireContext(), uiModel?.status)
        tvPaymentType.text = PaymentType.toResString(requireContext(), uiModel?.paymentType)

        uiModel?.selectedServices?.forEach { selectedService ->
            val bookingPrice = BookingPrice(
                service = selectedService.serviceName,
                price = selectedService.servicePrice,
                typeStringColor = R.color.color_dark1
            )

            val item = PricingDetailItemView(requireContext()).apply {
                setPricingItem(bookingPrice)
            }

            llBookingServicesAndPrices.addView(item)
        }

        ivBackIcon.setOnClickListener { findNavController().navigateUp() }
        btnDownloadReceipt.setOnClickListener { }
    }

    override suspend fun collectUiStates() {
        viewModel.uiState.collect { bitmap ->
            binding.ivQrCode.setImageBitmap(bitmap)
        }
    }

    override suspend fun collectUiEvents() {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is ShowError -> {
                    toast(message = event.message)
                }
            }
        }
    }
}
