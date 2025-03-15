package com.zapplications.salonbooking.data.datasource.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val LOCAL_SETTINGS_PREFERENCES = "local_settings"
val Context.settingsDataStore by preferencesDataStore(name = LOCAL_SETTINGS_PREFERENCES)

class SettingsLocalDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val isNavigatedToAppPermissions =
        booleanPreferencesKey(IS_NAVIGATED_TO_APP_PERMISSIONS_KEY)

    val isNavigatedToAppPermissionsFlow: Flow<Boolean>
        get() = context.settingsDataStore.data.map { preferences ->
            preferences[isNavigatedToAppPermissions] ?: false
        }

    suspend fun setNavigatedToAppPermissions(isNavigated: Boolean) = withContext(Dispatchers.IO) {
        context.settingsDataStore.edit { preferences ->
            preferences[isNavigatedToAppPermissions] = isNavigated
        }
    }

    companion object {
        const val IS_NAVIGATED_TO_APP_PERMISSIONS_KEY = "is_navigated_to_app_permissions"
    }
}
