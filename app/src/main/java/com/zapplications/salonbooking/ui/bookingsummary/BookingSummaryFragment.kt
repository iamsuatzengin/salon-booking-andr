package com.zapplications.salonbooking.ui.bookingsummary

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.viewBinding
import com.zapplications.salonbooking.databinding.FragmentBookingSummaryBinding
import com.zapplications.salonbooking.ui.shared.AppointmentSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingSummaryFragment : Fragment(R.layout.fragment_booking_summary) {
    private val binding by viewBinding(FragmentBookingSummaryBinding::bind)
    private val sharedViewModel: AppointmentSharedViewModel by navGraphViewModels(R.id.home_graph)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            ivBackIcon.setOnClickListener { findNavController().navigateUp() }
        }
    }
}