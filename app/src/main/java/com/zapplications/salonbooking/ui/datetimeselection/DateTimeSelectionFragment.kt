package com.zapplications.salonbooking.ui.datetimeselection

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.viewBinding
import com.zapplications.salonbooking.databinding.FragmentDateTimeSelectionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DateTimeSelectionFragment : Fragment(R.layout.fragment_date_time_selection) {
    private val binding by viewBinding(FragmentDateTimeSelectionBinding::bind)
    private val viewModel by viewModels<DateTimeSelectionViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getStylistAvailability(date = "2025-02-28")
    }
}
