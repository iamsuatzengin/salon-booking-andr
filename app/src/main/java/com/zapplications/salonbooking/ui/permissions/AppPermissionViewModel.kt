package com.zapplications.salonbooking.ui.permissions

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppPermissionViewModel @Inject constructor() : ViewModel() {
    var notificationPermissionGranted: Boolean = false
    var locationPermissionGranted: Boolean = false
}
