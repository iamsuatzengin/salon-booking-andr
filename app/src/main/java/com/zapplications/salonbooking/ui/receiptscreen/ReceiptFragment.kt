package com.zapplications.salonbooking.ui.receiptscreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.ui.pricingdetails.PricingDetailItemView
import com.zapplications.salonbooking.core.ui.pricingdetails.model.BookingPrice
import com.zapplications.salonbooking.core.viewBinding
import com.zapplications.salonbooking.databinding.FragmentReceiptBinding
import com.zapplications.salonbooking.domain.model.enums.BookingStatusType
import com.zapplications.salonbooking.domain.model.enums.PaymentType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReceiptFragment : Fragment(R.layout.fragment_receipt) {
    private val binding by viewBinding(FragmentReceiptBinding::bind)
    private val viewModel: ReceiptViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() = with(binding) {
        val uiModel = viewModel.bookingAppointmentUiModel
        viewModel.generateQrCodeBitmap { qrBitmap ->
            ivQrCode.setImageBitmap(qrBitmap)
        }

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
        btnDownloadReceipt.setOnClickListener {  }
    }
}
