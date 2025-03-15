package com.zapplications.salonbooking.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val isNavigatedToAppPermissionsFlow: Flow<Boolean>
    suspend fun setNavigatedToAppPermissions(isNavigated: Boolean)
}
