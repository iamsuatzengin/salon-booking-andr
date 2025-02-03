package com.zapplications.salonbooking.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.LocationUtil
import com.zapplications.salonbooking.core.extensions.checkLocationPermission
import com.zapplications.salonbooking.core.viewBinding
import com.zapplications.salonbooking.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)

    private var fusedLocationClient: FusedLocationProviderClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateLocationTextView()
    }

    private fun updateLocationTextView() {
        if (requireContext().checkLocationPermission()) {
            fusedLocationClient?.lastLocation?.addOnSuccessListener { location ->
                lifecycleScope.launch {
                    runCatching {
                        val address = LocationUtil.getFromLocation(
                            requireContext(),
                            location.longitude,
                            location.latitude
                        )
                        binding.tvLocation.text = getString(
                            R.string.text_location_subadminarea_adminarea,
                            address?.subAdminArea,
                            address?.adminArea
                        )
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        fusedLocationClient = null
    }
}
