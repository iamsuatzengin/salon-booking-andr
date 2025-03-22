package com.zapplications.salonbooking.ui.home

import androidx.lifecycle.viewModelScope
import com.zapplications.salonbooking.core.ui.BaseViewModel
import com.zapplications.salonbooking.domain.model.HomePageUiModel
import com.zapplications.salonbooking.domain.repository.HomeRepository
import com.zapplications.salonbooking.domain.usecase.itemusecases.GenerateHomeUiItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val generateHomeUiItems: GenerateHomeUiItems
) : BaseViewModel() {

    private val _homePageUiState = MutableStateFlow(HomeUiState.Empty)
    val homePageUiState get() = _homePageUiState.asStateFlow()

    fun getAllHomePageData(
        onLocationClick: () -> Unit,
    ) {
        call(
            block = {
                repository.getAllHomePageData()
            },
            onSuccess = { homePageUiModel ->
                generateUiItems(homePageUiModel, onLocationClick)
            }
        )
    }

    fun getHomePageDataByLocation(
        longitude: Double,
        latitude: Double,
        onLocationClick: () -> Unit,
    ) {
        call(
            block = {
                repository.getHomePageDataByLocation(
                    longitude = longitude,
                    latitude = latitude
                )
            },
            onSuccess = { homePageUiModel ->
                generateUiItems(homePageUiModel, onLocationClick)
            }
        )
    }

    private fun generateUiItems(uiModel: HomePageUiModel?, onLocationClick: () -> Unit) {
        val recyclerHomeUiItems = generateHomeUiItems(
            uiModel = uiModel,
            locationString = _homePageUiState.value.locationString,
            onLocationClick = onLocationClick,
            navigateToSalonDetail = ::navigateToSalonDetail
        )
        _homePageUiState.update { it.copy(homePageUiModel = recyclerHomeUiItems) }
    }

    fun updateLocation(locationString: String) {
        _homePageUiState.update { it.copy(locationString = locationString) }
    }

    private fun navigateToSalonDetail(salonId: String) {
        viewModelScope.launch {
            sendEvent(NavigateToSalonDetail(salonId))
        }
    }
}
