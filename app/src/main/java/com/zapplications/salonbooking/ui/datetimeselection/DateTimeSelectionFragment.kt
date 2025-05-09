package com.zapplications.salonbooking.ui.datetimeselection

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.adapter.decoration.MultiTypeMarginDecoration
import com.zapplications.salonbooking.core.ui.BaseFragment
import com.zapplications.salonbooking.core.ui.applyinset.InsetSides
import com.zapplications.salonbooking.core.viewBinding
import com.zapplications.salonbooking.databinding.FragmentDateTimeSelectionBinding
import com.zapplications.salonbooking.ui.datetimeselection.adapter.DateTimeSelectionAdapter
import com.zapplications.salonbooking.ui.shared.AppointmentSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DateTimeSelectionFragment : BaseFragment<DateTimeSelectionViewModel>(R.layout.fragment_date_time_selection) {
    private val binding by viewBinding(FragmentDateTimeSelectionBinding::bind)

    override val viewModel by viewModels<DateTimeSelectionViewModel>()
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
    }

    override suspend fun collectUiStates() {
        viewModel.uiState.collect { uiState ->
            adapter.submitList(uiState.items.toList())

            binding.btnConfirm.isEnabled = uiState.isConfirmButtonEnabled
        }
    }

    private fun initRecyclerView() = with(binding) {
        rvMultiType.adapter = adapter
        rvMultiType.setHasFixedSize(true)
        rvMultiType.itemAnimator = null
        rvMultiType.addItemDecoration(MultiTypeMarginDecoration())
    }

    override fun canRootViewApplyInset(): Boolean = true
    override fun adjustRootViewInsetSides(): InsetSides = InsetSides(top = true, bottom = true)
}
