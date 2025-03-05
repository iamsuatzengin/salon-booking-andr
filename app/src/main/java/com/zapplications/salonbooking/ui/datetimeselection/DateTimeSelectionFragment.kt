package com.zapplications.salonbooking.ui.datetimeselection

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
import com.zapplications.salonbooking.core.adapter.decoration.MultiTypeMarginDecoration
import com.zapplications.salonbooking.core.viewBinding
import com.zapplications.salonbooking.databinding.FragmentDateTimeSelectionBinding
import com.zapplications.salonbooking.ui.datetimeselection.adapter.DateTimeSelectionAdapter
import com.zapplications.salonbooking.ui.shared.AppointmentSharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DateTimeSelectionFragment : Fragment(R.layout.fragment_date_time_selection) {
    private val binding by viewBinding(FragmentDateTimeSelectionBinding::bind)
    private val viewModel by viewModels<DateTimeSelectionViewModel>()
    private val sharedViewModel: AppointmentSharedViewModel by navGraphViewModels(R.id.home_graph)
    private val adapter by lazy { DateTimeSelectionAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        binding.ivBackIcon.setOnClickListener { findNavController().navigateUp() }

        viewModel.generateUiAdapter()

        binding.btnConfirm.setOnClickListener {
            sharedViewModel.apply {
                selectedDate = viewModel.selectedDate
                selectedTime = viewModel.selectedTime?.timeUiModel
            }

            findNavController().navigate(R.id.actionDateTimeSelectionToBookingSummary)
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect { uiState ->
                        adapter.submitList(uiState.items.toList())

                        binding.btnConfirm.isEnabled = uiState.isConfirmButtonEnabled
                    }
                }

                launch {
                    viewModel.uiEvent.collect { uiEvent ->
                        when (uiEvent) {
                            DateTimeSelectionUiEvent.MoreDate -> {
                                // TODO handle on more dates click. It should open a bottom sheet for selecting date
                            }
                        }
                    }
                }

            }
        }
    }

    private fun initRecyclerView() = with(binding) {
        rvMultiType.adapter = adapter
        rvMultiType.setHasFixedSize(true)
        rvMultiType.itemAnimator = null
        rvMultiType.addItemDecoration(MultiTypeMarginDecoration())
    }
}
