package com.zapplications.salonbooking.ui.bookingsummary

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.navigation.navOptions
import com.zapplications.salonbooking.MainGraphDirections
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.extensions.CORNER_RADIUS_DEFAULT_PX
import com.zapplications.salonbooking.core.extensions.ZERO
import com.zapplications.salonbooking.core.extensions.loadImage
import com.zapplications.salonbooking.core.extensions.toast
import com.zapplications.salonbooking.core.ui.BaseFragment
import com.zapplications.salonbooking.core.ui.ShowError
import com.zapplications.salonbooking.core.ui.applyinset.InsetSides
import com.zapplications.salonbooking.core.ui.dialog.statusdialog.ButtonConfig
import com.zapplications.salonbooking.core.ui.dialog.statusdialog.StatusDialog
import com.zapplications.salonbooking.core.ui.dialog.statusdialog.StatusDialogState
import com.zapplications.salonbooking.core.ui.dialog.statusdialog.statusDialogBuilder
import com.zapplications.salonbooking.core.ui.pricingdetails.model.BookingPrice
import com.zapplications.salonbooking.core.ui.pricingdetails.model.BookingPricingDetail
import com.zapplications.salonbooking.core.viewBinding
import com.zapplications.salonbooking.databinding.FragmentBookingSummaryBinding
import com.zapplications.salonbooking.domain.model.BookingAppointmentUiModel
import com.zapplications.salonbooking.domain.model.SelectedServices
import com.zapplications.salonbooking.domain.model.ServiceUiModel
import com.zapplications.salonbooking.domain.model.enums.PaymentType
import com.zapplications.salonbooking.ui.shared.AppointmentSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingSummaryFragment : BaseFragment<BookingSummaryViewModel>(R.layout.fragment_booking_summary) {
    private val binding by viewBinding(FragmentBookingSummaryBinding::bind)
    private val sharedViewModel: AppointmentSharedViewModel by navGraphViewModels(R.id.home_graph)
    override val viewModel: BookingSummaryViewModel by viewModels()

    private var loadingStatusDialog: StatusDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    override suspend fun collectUiStates() {
        viewModel.uiState.collect { uiState ->
            if (uiState.isLoading) {
                loading()
            } else {
                loadingStatusDialog?.dismissNow()
            }
        }
    }

    override suspend fun collectUiEvents() {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is BookingAppointmentSuccessFull ->
                    success(uiEvent.bookingAppointmentUiModel)

                is ShowError -> failed()
            }
        }
    }

    private fun initView() {
        initSalonCard()
        binding.tvBookingDate.text = getString(
            R.string.text_booking_date,
            sharedViewModel.selectedDate?.dayOfWeekText,
            sharedViewModel.selectedDate?.month,
            sharedViewModel.selectedDate?.dayOfMonthNumber,
            sharedViewModel.selectedTime?.time.toString()
        )

        binding.tvStylist.text = getString(
            R.string.text_stylist,
            sharedViewModel.getSelectedStylistName() ?: getString(R.string.title_any_stylist),
            sharedViewModel.salon?.services?.firstOrNull()?.customDuration
                ?: sharedViewModel.salon?.services?.firstOrNull()?.defaultDuration
        )

        sharedViewModel.selectedServices?.let { initPricingDetails(it) }

        handleClickEvents()
    }

    private fun initSalonCard() = with(binding.layoutSalonCard) {
        sharedViewModel.salon?.apply {
            tvSalonName.text = salonName

            loadImage(
                url = imageUrl,
                target = ivSalonImage,
                radius = CORNER_RADIUS_DEFAULT_PX
            )
            tvSalonLocationString.text = address
            tvRating.text = getString(
                R.string.text_rating,
                rating,
                reviewerCount
            )
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
            toast(getString(R.string.message_please_select_payment_type))
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
            stylistId = sharedViewModel.getSelectedStylistId(),
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

    private fun loading() {
        val statusDialog = statusDialogBuilder {
            title(getString(R.string.title_status_dialog_payment_loading))
            description(getString(R.string.description_status_dialog_payment_loading))
            state(StatusDialogState.LOADING)
        }

        loadingStatusDialog = statusDialog.show(childFragmentManager, LOADING_DIALOG_TAG)
    }

    private fun success(bookingAppointmentUiModel: BookingAppointmentUiModel) {
        statusDialogBuilder {
            title(getString(R.string.title_status_dialog_appointment_confirmed))
            description(getString(R.string.description_status_dialog_confirmed))
            state(StatusDialogState.SUCCESS)
            buttons(
                listOf(
                    ButtonConfig(
                        title = getString(R.string.btn_view_receipt),
                        action = {
                            val action = MainGraphDirections.actionToReceiptFragment(
                                bookingAppointmentUiModel = bookingAppointmentUiModel
                            )
                            sharedViewModel.clear()
                            findNavController().navigate(
                                directions = action,
                                navOptions = navOptions {
                                    popUpTo(R.id.homeFragment) {
                                        inclusive = false
                                    }
                                }
                            )
                        }
                    ),
                    ButtonConfig(
                        title = getString(R.string.btn_back_to_home),
                        action = {
                            findNavController().popBackStack(R.id.homeFragment, false)
                        }
                    )
                )
            )
            show(childFragmentManager, SUCCESS_DIALOG_TAG)
        }
    }

    private fun failed() {
        statusDialogBuilder {
            title(getString(R.string.title_status_dialog_payment_failed))
            description(getString(R.string.description_status_dialog_payment_failed))
            state(StatusDialogState.FAILED)
            buttons(
                listOf(
                    ButtonConfig(
                        title = getString(R.string.btn_try_again),
                        action = { dismiss() }
                    ),
                    ButtonConfig(
                        title = getString(R.string.btn_change_payment_method),
                        action = { dismiss() }
                    )
                )
            )
            show(childFragmentManager, FAILED_DIALOG_TAG)
        }
    }

    override fun canRootViewApplyInset(): Boolean = true
    override fun adjustRootViewInsetSides(): InsetSides = InsetSides(top = true, bottom = true)

    companion object {
        const val SUCCESS_DIALOG_TAG = "SUCCESS"
        const val FAILED_DIALOG_TAG = "FAILED"
        const val LOADING_DIALOG_TAG = "LOADING"
    }
}