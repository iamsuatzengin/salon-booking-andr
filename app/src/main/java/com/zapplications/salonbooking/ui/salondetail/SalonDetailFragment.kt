package com.zapplications.salonbooking.ui.salondetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.viewBinding
import com.zapplications.salonbooking.databinding.FragmentSalonDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SalonDetailFragment : Fragment(R.layout.fragment_salon_detail) {
    private val binding by viewBinding(FragmentSalonDetailBinding::bind)
    private val viewModel: SalonDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSalonDetail()

        collectData()
    }

    private fun collectData() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiModel ->
                    binding.tv.text = uiModel?.salonName ?: "Salon Name"
                }
            }
        }
    }
}
