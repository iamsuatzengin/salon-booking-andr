package com.zapplications.salonbooking.ui.stylistlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.viewBinding
import com.zapplications.salonbooking.databinding.FragmentStylistListBinding
import com.zapplications.salonbooking.domain.model.StylistUiModel
import com.zapplications.salonbooking.core.decoration.MarginDecoration
import com.zapplications.salonbooking.ui.stylistlist.adapter.StylistAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StylistListFragment : Fragment(R.layout.fragment_stylist_list) {
    private val binding by viewBinding(FragmentStylistListBinding::bind)
    private val viewModel: StylistListViewModel by viewModels()
    private var adapter: StylistAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getStylistsBySalonId()
        initRecyclerView()
        collectData()
    }

    private fun collectData() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiModel -> initView(uiModel) }
            }
        }
    }

    private fun initView(uiModel: List<StylistUiModel>) {
        adapter?.submitList(uiModel)
    }

    private fun initRecyclerView() {
        adapter = StylistAdapter()
        binding.rvStylists.addItemDecoration(
            MarginDecoration(
                excludeLastItem = true,
                marginBottomPx = 16
            )
        )
        binding.rvStylists.adapter = adapter
    }
}
