package com.zapplications.salonbooking.ui.stylistlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.adapter.decoration.MarginDecoration
import com.zapplications.salonbooking.core.ui.BaseFragment
import com.zapplications.salonbooking.core.viewBinding
import com.zapplications.salonbooking.databinding.FragmentStylistListBinding
import com.zapplications.salonbooking.ui.shared.AppointmentSharedViewModel
import com.zapplications.salonbooking.ui.stylistlist.adapter.StylistAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StylistListFragment : BaseFragment<StylistListViewModel>(R.layout.fragment_stylist_list) {
    private val binding by viewBinding(FragmentStylistListBinding::bind)

    override val viewModel: StylistListViewModel by viewModels()
    private val sharedViewModel: AppointmentSharedViewModel by navGraphViewModels(R.id.home_graph)

    private var adapter: StylistAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getStylistsBySalonId()
        initRecyclerView()

        binding.ivBackIcon.setOnClickListener { findNavController().navigateUp() }
        binding.btnSelectAndContinue.setOnClickListener {
            viewModel.selectedStylist?.let {
                sharedViewModel.selectedStylist = viewModel.selectedStylist
                navigateToDateTimeSelection(stylistId = sharedViewModel.getSelectedStylistId())
            }
        }
    }

    override suspend fun collectUiStates() {
        viewModel.uiState.collect { uiState ->
            initView(uiState)
        }
    }

    private fun initView(uiState: StylistListUiState) {
        adapter?.submitList(uiState.uiItems)
        binding.btnSelectAndContinue.isEnabled = uiState.buttonEnabled
    }

    private fun initRecyclerView() {
        adapter = StylistAdapter()
        binding.rvStylists.addItemDecoration(
            MarginDecoration(
                excludeLastItem = true,
                marginBottomPx = 16
            )
        )
        binding.rvStylists.itemAnimator = null
        binding.rvStylists.adapter = adapter
    }

    private fun navigateToDateTimeSelection(stylistId: String) {
        val action =
            StylistListFragmentDirections.actionStylistListToDateTimeSelection(stylistId = stylistId)
        findNavController().navigate(action)
    }
}
