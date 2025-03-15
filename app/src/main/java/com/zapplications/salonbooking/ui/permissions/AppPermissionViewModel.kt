package com.zapplications.salonbooking.ui.permissions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zapplications.salonbooking.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppPermissionViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    var notificationPermissionGranted: Boolean = false
    var locationPermissionGranted: Boolean = false

    fun setNavigatedToAppPermissions(
        onComplete: () -> Unit
    ) {
        viewModelScope.launch {
            runCatching {
                settingsRepository.setNavigatedToAppPermissions(true)
                onComplete()
            }
        }
    }
}
