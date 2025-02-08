package com.zapplications.salonbooking.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.LocationUtil
import com.zapplications.salonbooking.core.LocationUtil.getLocationPermission
import com.zapplications.salonbooking.core.extensions.checkLocationPermission
import com.zapplications.salonbooking.core.extensions.checkLocationProviderEnabled
import com.zapplications.salonbooking.core.ui.dialog.CustomDialog
import com.zapplications.salonbooking.core.viewBinding
import com.zapplications.salonbooking.databinding.FragmentHomeBinding
import com.zapplications.salonbooking.ui.home.adapter.HomeAdapter
import com.zapplications.salonbooking.ui.home.adapter.decorator.MarginDecorator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels()

    private val adapter by lazy { HomeAdapter() }

    private var fusedLocationClient: FusedLocationProviderClient? = null

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val locationPermissionGranted = permissions.entries.all { it.value }

        if (!locationPermissionGranted) return@registerForActivityResult

        if (!requireContext().checkLocationProviderEnabled()) {
            openLocationSettings()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        viewModel.getAllHomePageData(
            onLocationClick = { handleLocationTitleClick() }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.homePageUiState.collect { state ->
                    adapter.submitList(state.homePageUiModel)
                }
            }
        }

        if (!isLocationAvailable()) {
            // TODO request for all salon
        }
    }

    override fun onResume() {
        super.onResume()

        updateLocation()
    }

    private fun updateLocation() {
        if (requireContext().checkLocationPermission() && requireContext().checkLocationProviderEnabled()) {
            fusedLocationClient?.lastLocation?.addOnSuccessListener { location ->
                lifecycleScope.launch {
                    runCatching {
                        val address = LocationUtil.getFromLocation(
                            context = requireContext(),
                            longitude = location.longitude,
                            latitude = location.latitude
                        )

                        viewModel.updateLocation(
                            getString(
                                R.string.text_location_subadminarea_adminarea,
                                address?.subAdminArea,
                                address?.adminArea
                            )
                        )
                        // request - param(longitude, latitude)
                    }
                }
            }?.addOnFailureListener {
                Log.e("HomeFragment", "Exception occurred!", it)
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvHome.adapter = adapter
        binding.rvHome.itemAnimator = null
        binding.rvHome.addItemDecoration(MarginDecorator())
    }

    private fun handleLocationTitleClick() {
        when {
            !requireContext().checkLocationPermission() -> {
                requestLocationPermission()
            }

            !requireContext().checkLocationProviderEnabled() -> {
                openLocationSettings()
            }
        }
    }

    private fun requestLocationPermission() {
        CustomDialog()
            .title(getString(R.string.title_location_permission))
            .description(getString(R.string.description_location_permission))
            .positiveButton(text = getString(R.string.btn_text_yes)) {
                permissionLauncher.launch(getLocationPermission())
                dismiss()
            }
            .negativeButton(text = getString(R.string.btn_text_no)) { dismiss() }
            .show(childFragmentManager, LOCATION_PERMISSION_DIALOG_TAG)
    }

    private fun openLocationSettings() {
        CustomDialog()
            .title(getString(R.string.title_location_settings))
            .description(getString(R.string.description_location_settings))
            .positiveButton(text = getString(R.string.btn_text_yes)) {
                LocationUtil.openLocationSettings(requireContext())
                dismiss()
            }
            .negativeButton(text = getString(R.string.btn_text_no)) { dismiss() }
            .show(childFragmentManager, LOCATION_SETTINGS_DIALOG_TAG)
    }

    private fun isLocationAvailable() =
        requireContext().checkLocationPermission() && requireContext().checkLocationProviderEnabled()

    override fun onDestroy() {
        super.onDestroy()

        fusedLocationClient = null
    }

    companion object {
        const val LOCATION_PERMISSION_DIALOG_TAG = "LOCATION_PERMISSION_DIALOG_TAG"
        const val LOCATION_SETTINGS_DIALOG_TAG = "LOCATION_SETTINGS_DIALOG_TAG"
    }
}
