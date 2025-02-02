package com.zapplications.salonbooking.ui.permissions

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.extensions.checkLocationPermission
import com.zapplications.salonbooking.core.extensions.checkNotificationPermission
import com.zapplications.salonbooking.core.viewBinding
import com.zapplications.salonbooking.databinding.FragmentAppPermissionsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AppPermissionsFragment : Fragment(R.layout.fragment_app_permissions) {
    private val binding by viewBinding(FragmentAppPermissionsBinding::bind)
    private val viewModel: AppPermissionViewModel by viewModels()

    private val singlePermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            setNotificationSwitch(true)
        } else {
            binding.switchNotification.apply {
                isChecked = false
                isEnabled = false
            }
        }
    }

    private val multiplePermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val locationPermissionGranted = permissions.entries.all { it.value }
        if (locationPermissionGranted) {
            setLocationSwitch(true)
        } else {
            binding.switchLocation.apply {
                isChecked = false
                isEnabled = false
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSwitchesState()

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            binding.switchNotification.isEnabled = false
        }

        initListeners()
    }

    override fun onResume() {
        super.onResume()
        setSwitchesState()
    }

    private fun initListeners() = with(binding) {
        switchNotification.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked && !viewModel.notificationPermissionGranted) {
                singlePermissionLauncher.launch(NOTIFICATION_PERMISSION)
            }
        }

        switchLocation.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked && !viewModel.locationPermissionGranted) {
                multiplePermissionLauncher.launch(getLocationPermission())
            }
        }

        btnContinue.setOnClickListener {
            findNavController().navigate(R.id.appPermissionsToHomeGraph)
        }

        tvBottomInfo.setOnClickListener { navigateToSettingsAppForPermission() }
    }

    private fun setSwitchesState() {
        with(requireContext()) {
            viewModel.notificationPermissionGranted = checkNotificationPermission()
            viewModel.locationPermissionGranted = checkLocationPermission()
        }
        setNotificationSwitch(viewModel.notificationPermissionGranted)
        setLocationSwitch(viewModel.locationPermissionGranted)
    }

    private fun setNotificationSwitch(permissionGranted: Boolean) {
        viewModel.notificationPermissionGranted = permissionGranted
        binding.switchNotification.isChecked = permissionGranted
        binding.switchNotification.isEnabled = !permissionGranted
    }

    private fun setLocationSwitch(permissionGranted: Boolean) {
        viewModel.locationPermissionGranted = permissionGranted
        binding.switchLocation.isChecked = permissionGranted
        binding.switchLocation.isEnabled = !permissionGranted
    }

    private fun getLocationPermission() = arrayOf(
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    private fun navigateToSettingsAppForPermission() = runCatching {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", requireContext().packageName, null)
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    companion object {
        private const val NOTIFICATION_PERMISSION = android.Manifest.permission.POST_NOTIFICATIONS
    }
}
