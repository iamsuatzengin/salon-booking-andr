package com.zapplications.salonbooking.ui.bookings.pager

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.adapter.decoration.MarginDecoration
import com.zapplications.salonbooking.core.extensions.toast
import com.zapplications.salonbooking.core.viewBinding
import com.zapplications.salonbooking.databinding.FragmentBookingsPagerItemBinding
import com.zapplications.salonbooking.domain.model.BookingsUiModel
import com.zapplications.salonbooking.domain.model.enums.BookingStatusType
import com.zapplications.salonbooking.ui.bookings.BookingsViewModel
import com.zapplications.salonbooking.ui.bookings.adapter.BookingItemClickHandler
import com.zapplications.salonbooking.ui.bookings.adapter.BookingsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookingsPagerItemFragment : Fragment(R.layout.fragment_bookings_pager_item),
    BookingItemClickHandler {
    private val binding by viewBinding(FragmentBookingsPagerItemBinding::bind)
    private val viewModel by viewModels<BookingsViewModel>()
    private val adapter by lazy { BookingsAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.takeIf { it.containsKey(BOOKING_STATUS_TYPE) }?.apply {
            val bookingStatus = arguments?.getString(BOOKING_STATUS_TYPE)
            viewModel.getUserBookings(bookingStatus)
        }

        initRecyclerView()
        collectData()
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

    private fun collectData() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { bookings ->
                    binding.emptyState.root.isVisible = bookings.isEmpty()
                    binding.rvBookings.isVisible = bookings.isNotEmpty()

                    adapter.submitList(bookings)
                }
            }
        }
    }

    override fun onViewReceiptClick(item: BookingsUiModel) {
        toast(message = "View receipt clicked for ${item.salon?.salonName}")
    }

    override fun onCancelBookingClick(item: BookingsUiModel) {
        toast(message = "Cancel booking clicked for ${item.salon?.salonName}")
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