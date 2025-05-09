package com.zapplications.salonbooking.ui.home

import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.LocationUtil
import com.zapplications.salonbooking.core.LocationUtil.getLocationPermission
import com.zapplications.salonbooking.core.UiEvent
import com.zapplications.salonbooking.core.adapter.decoration.MultiTypeMarginDecoration
import com.zapplications.salonbooking.core.extensions.ONE
import com.zapplications.salonbooking.core.extensions.checkLocationPermission
import com.zapplications.salonbooking.core.extensions.checkLocationProviderEnabled
import com.zapplications.salonbooking.core.ui.BaseFragment
import com.zapplications.salonbooking.core.ui.applyinset.InsetSides
import com.zapplications.salonbooking.core.ui.applyinset.applySystemBarInsetsAsPadding
import com.zapplications.salonbooking.core.ui.dialog.CustomDialog
import com.zapplications.salonbooking.core.viewBinding
import com.zapplications.salonbooking.databinding.FragmentHomeBinding
import com.zapplications.salonbooking.ui.home.adapter.HomeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel>(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    override val viewModel: HomeViewModel by viewModels()

    private val adapter by lazy { HomeAdapter() }

    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(location: LocationResult) {
            super.onLocationResult(location)
            updateLocation(location.lastLocation)
        }
    }

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

        getLastKnownLocation()

        if (!isLocationAvailable()) {
            viewModel.getAllHomePageData(
                onLocationClick = { handleLocationTitleClick() }
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
    }

    override suspend fun collectUiStates() {
        viewModel.homePageUiState.collect { state ->
            adapter.submitList(state.homePageUiModel)
        }
    }

    override suspend fun collectUiEvents() {
        viewModel.uiEvent.collect { event ->
            handleUiEvent(event)
        }
    }

    private fun getLastKnownLocation() {
        if (requireContext().checkLocationPermission() && requireContext().checkLocationProviderEnabled()) {
            fusedLocationClient?.lastLocation?.addOnSuccessListener { location ->
                updateLocation(location)
            }?.addOnFailureListener {
                Log.e("HomeFragment", "Exception occurred!", it)
            }
        }
    }

    private fun updateLocation(location: Location?) {
        lifecycleScope.launch {
            runCatching {
                if (location == null) {
                    requestLocationUpdates()
                    return@launch
                }

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
                // TODO burası böyle pushlanmayacak
                viewModel.getAllHomePageData(
                    onLocationClick = {handleLocationTitleClick()}
                )
                /*viewModel.getHomePageDataByLocation(
                    longitude = location.longitude,
                    latitude = location.latitude,
                    onLocationClick = { handleLocationTitleClick() }
                )*/
            }
        }
    }

    private fun initRecyclerView() = with(binding) {
        rvHome.adapter = adapter
        rvHome.itemAnimator = null
        rvHome.addItemDecoration(MultiTypeMarginDecoration())

        rvHome.applySystemBarInsetsAsPadding(InsetSides(top = true))
    }

    private fun handleUiEvent(event: UiEvent) {
        when (event) {
            is NavigateToSalonDetail -> {
                val action = HomeFragmentDirections.actionHomeToSalonDetail(event.salonId)
                findNavController().navigate(action)
            }
        }
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

    private fun requestLocationUpdates() {
        if (requireContext().checkLocationPermission()) {
            val locationRequest = LocationRequest.Builder(5000)
                .setMaxUpdates(ONE)
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .build()

            fusedLocationClient?.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient?.removeLocationUpdates(locationCallback)
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationClient = null
    }

    companion object {
        const val LOCATION_PERMISSION_DIALOG_TAG = "LOCATION_PERMISSION_DIALOG_TAG"
        const val LOCATION_SETTINGS_DIALOG_TAG = "LOCATION_SETTINGS_DIALOG_TAG"
    }
}
