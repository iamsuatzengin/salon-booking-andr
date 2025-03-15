package com.zapplications.salonbooking.ui.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.extensions.checkLocationPermission
import com.zapplications.salonbooking.core.extensions.checkNotificationPermission
import com.zapplications.salonbooking.core.viewBinding
import com.zapplications.salonbooking.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {
    private val binding by viewBinding(FragmentSplashBinding::bind)
    private val viewModel: SplashViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect { event ->
                    when (event) {
                        SplashUiEvent.NavigateToSignIn -> {
                            findNavController().navigate(R.id.splashToSignIn)
                        }

                        is SplashUiEvent.NavigateToHome -> {
                            checkPermissionsAndNavigate(event.isNavigatedAppPermissionBefore)
                        }
                    }
                }
            }
        }
    }

    private fun checkPermissionsAndNavigate(navigatedAppPermissionBefore: Boolean) {
        with(requireContext()) {
            if (navigatedAppPermissionBefore) {
                findNavController().navigate(R.id.splashToHome)
                return
            }

            if (checkNotificationPermission() && checkLocationPermission()) {
                findNavController().navigate(R.id.splashToHome)
            } else {
                findNavController().navigate(R.id.splashToAppPermissions)
            }
        }
    }
}
