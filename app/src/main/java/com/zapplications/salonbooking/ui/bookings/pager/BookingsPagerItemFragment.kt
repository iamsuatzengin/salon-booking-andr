package com.zapplications.salonbooking.ui.bookings.pager

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zapplications.salonbooking.MainGraphDirections
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.ui.BaseFragment
import com.zapplications.salonbooking.core.adapter.decoration.MarginDecoration
import com.zapplications.salonbooking.core.ui.bottomsheet.MyBottomSheet
import com.zapplications.salonbooking.core.ui.bottomsheet.MyBottomSheetParam
import com.zapplications.salonbooking.core.ui.dialog.statusdialog.ButtonConfig
import com.zapplications.salonbooking.core.ui.dialog.statusdialog.StatusDialogState
import com.zapplications.salonbooking.core.ui.dialog.statusdialog.statusDialogBuilder
import com.zapplications.salonbooking.core.viewBinding
import com.zapplications.salonbooking.databinding.FragmentBookingsPagerItemBinding
import com.zapplications.salonbooking.domain.model.BookingAppointmentUiModel
import com.zapplications.salonbooking.domain.model.BookingsUiModel
import com.zapplications.salonbooking.domain.model.enums.BookingStatusType
import com.zapplications.salonbooking.ui.bookings.BookingCancelledSuccess
import com.zapplications.salonbooking.ui.bookings.BookingsViewModel
import com.zapplications.salonbooking.ui.bookings.NavigateToReceipt
import com.zapplications.salonbooking.ui.bookings.adapter.BookingItemClickHandler
import com.zapplications.salonbooking.ui.bookings.adapter.BookingsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingsPagerItemFragment : BaseFragment<BookingsViewModel>(R.layout.fragment_bookings_pager_item),
    BookingItemClickHandler {
    private val binding by viewBinding(FragmentBookingsPagerItemBinding::bind)
    override val viewModel by viewModels<BookingsViewModel>()

    private val adapter by lazy { BookingsAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.takeIf { it.containsKey(BOOKING_STATUS_TYPE) }?.apply {
            val bookingStatus = arguments?.getString(BOOKING_STATUS_TYPE)
            viewModel.getUserBookings(bookingStatus)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.apply {
            emptyState.apply {
                ivEmptyState.setImageResource(R.drawable.ic_cart_empty)
                tvEmptyStateTitle.text = getString(R.string.cart_empty_state_title)
                tvEmptyStateBody.text = getString(R.string.cart_empty_state_body)
            }

            rvBookings.layoutManager = LinearLayoutManager(requireContext())
            rvBookings.itemAnimator = null
            rvBookings.addItemDecoration(
                MarginDecoration(
                    marginBottomPx = 16
                )
            )
            rvBookings.adapter = adapter
        }
    }

    override suspend fun collectUiEvents() {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is NavigateToReceipt -> {
                    navigateToReceiptFragment(uiEvent.bookingAppointmentUiModel)
                }

                BookingCancelledSuccess -> showBookingCancelledSuccessInfoDialog()
            }
        }
    }

    override suspend fun collectUiStates() {
        viewModel.uiState.collect { bookings ->
            binding.emptyState.root.isVisible = bookings.isEmpty()
            binding.rvBookings.isVisible = bookings.isNotEmpty()

            adapter.submitList(bookings)
        }
    }

    private fun navigateToReceiptFragment(bookingAppointmentUiModel: BookingAppointmentUiModel) {
        val action = MainGraphDirections.actionToReceiptFragment(bookingAppointmentUiModel)
        findNavController().navigate(action)
    }

    override fun onViewReceiptClick(item: BookingsUiModel) {
        viewModel.getBookingById(item.id)
    }

    override fun onCancelBookingClick(item: BookingsUiModel) {
        MyBottomSheet
            .newInstance(
                MyBottomSheetParam(
                    title = "Cancel Booking",
                    subtitle = "Are you sure you want to cancel?",
                    description = "Canceling your appointment will remove it from your upcoming bookings.",
                    buttons = listOf(
                        ButtonConfig(
                            title = "Keep Appointment",
                            action = { dismiss() }
                        ),
                        ButtonConfig(
                            title = "Yes, Cancel Booking",
                            action = {
                                viewModel.cancelBooking(item.id)
                                dismiss()
                            }
                        )
                    )
                )
            )
            .show(childFragmentManager, "MyBottomSheetBookings")
    }

    private fun showBookingCancelledSuccessInfoDialog() {
        statusDialogBuilder {
            state(StatusDialogState.SUCCESS)
            title(getString(R.string.title_booking_cancelled))
            description(getString(R.string.description_booking_cancelled))
            buttons(
                listOf(
                    ButtonConfig(
                        title = getString(R.string.btn_text_back_to_bookings),
                        action = { dismiss() }
                    )
                )
            )
            show(childFragmentManager, "StatusDialogSuccess")
        }
    }

    companion object {
        const val BOOKING_STATUS_TYPE = "booking_status_type"

        fun newInstance(bookingStatus: BookingStatusType): BookingsPagerItemFragment {
            return BookingsPagerItemFragment().apply {
                arguments = bundleOf().apply {
                    putString(BOOKING_STATUS_TYPE, bookingStatus.name)
                }
            }
        }
    }
}