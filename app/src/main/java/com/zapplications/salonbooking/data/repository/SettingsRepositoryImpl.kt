package com.zapplications.salonbooking.data.repository

import com.zapplications.salonbooking.data.datasource.local.SettingsLocalDataSource
import com.zapplications.salonbooking.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settingsLocalDataSource: SettingsLocalDataSource
): SettingsRepository {
    override val isNavigatedToAppPermissionsFlow: Flow<Boolean>
        get() = settingsLocalDataSource.isNavigatedToAppPermissionsFlow

    override suspend fun setNavigatedToAppPermissions(isNavigated: Boolean) {
        settingsLocalDataSource.setNavigatedToAppPermissions(isNavigated)
    }
}
