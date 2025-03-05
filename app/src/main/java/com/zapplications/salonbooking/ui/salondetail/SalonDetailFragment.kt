package com.zapplications.salonbooking.ui.salondetail

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.extensions.ONE
import com.zapplications.salonbooking.core.extensions.ZERO
import com.zapplications.salonbooking.core.extensions.loadImage
import com.zapplications.salonbooking.core.extensions.scaleAnimation
import com.zapplications.salonbooking.core.extensions.scaleVisibilityAnimation
import com.zapplications.salonbooking.core.ui.tabview.CustomTabView
import com.zapplications.salonbooking.core.viewBinding
import com.zapplications.salonbooking.databinding.FragmentSalonDetailBinding
import com.zapplications.salonbooking.domain.model.SalonUiModel
import com.zapplications.salonbooking.domain.model.ServiceUiModel
import com.zapplications.salonbooking.ui.shared.AppointmentSharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SalonDetailFragment : Fragment(R.layout.fragment_salon_detail),
    CustomTabView.TabChangeListener {
    private val binding by viewBinding(FragmentSalonDetailBinding::bind)
    private val viewModel: SalonDetailViewModel by viewModels()
    private val sharedViewModel: AppointmentSharedViewModel by navGraphViewModels(R.id.home_graph)

    private var isButtonAnimated = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSalonDetail()
        collectData()
        handleClickListeners()

        binding.customTabView.observable.addObserver {
            binding.btnContinue.apply {
                isVisible = it.isNotEmpty()
                text = getString(R.string.btn_text_continue_with_selected_count, it.size)
                scrollToButton()

                if (!isVisible) isButtonAnimated = false; animate().cancel()

                if (isVisible && it.size == ONE && !isButtonAnimated) {
                    isButtonAnimated = true
                    scaleVisibilityAnimation()
                }
            }
        }
    }

    private fun collectData() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiModel ->
                    initView(uiModel)
                }
            }
        }
    }

    private fun initView(uiModel: SalonUiModel?) = with(binding) {
        uiModel?.run {
            tvSalonName.text = salonName
            tvSalonLocation.text = address
            tvSalonWorkHours.text = workHours
            tvSalonDescription.text = description
            tvSalonRating.text = buildString {
                append(rating)
                append(" (")
                append(reviewerCount)
                append(")")
            }

            initCustomTab(services)
            initSalonImage(uiModel.imageUrl)
        }
    }

    private fun initCustomTab(services: List<ServiceUiModel>) = with(binding.customTabView) {
        tabItems = services.groupBy { it.category }
        tabChangeListener = this@SalonDetailFragment
        initTabs()
    }

    private fun initSalonImage(url: String) {
        loadImage(
            url = url,
            target = binding.ivSalonImage,
            radius = resources.getDimensionPixelSize(R.dimen.corner_radius_default)
        )
    }

    private fun handleClickListeners() {
        binding.ivBackIcon.setOnClickListener { findNavController().navigateUp() }

        binding.btnContinue.setOnClickListener {
            val action = SalonDetailFragmentDirections.actionSalonDetailToStylistList(viewModel.salonId)
            sharedViewModel.apply {
                salon = viewModel.uiState.value
                selectedServices = binding.customTabView.selectedServices
            }
            findNavController().navigate(action)
        }

        binding.ivFavoriteIcon.setOnClickListener {
            viewModel.isFavoriteSelected = !viewModel.isFavoriteSelected
            binding.ivFavoriteIcon.setImageResource(viewModel.favoriteIcon)

            binding.ivFavoriteIcon.scaleAnimation()
        }
    }

    private fun scrollToButton() = with(binding.btnContinue) {
        if (isVisible) {
            lifecycleScope.launch {
                delay(50)
                binding.root.smoothScrollTo(ZERO, binding.root.height)
            }
        }
    }

    override fun onTabChanged(position: Int) {
        scrollToButton()
    }
}
