package com.zapplications.salonbooking.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zapplications.salonbooking.domain.model.HomePageUiModel
import com.zapplications.salonbooking.domain.repository.HomeRepository
import com.zapplications.salonbooking.domain.usecase.itemusecases.GenerateHomeUiItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val generateHomeUiItems: GenerateHomeUiItems
) : ViewModel() {

    private val _homePageUiState = MutableStateFlow(HomeUiState.Empty)
    val homePageUiState get() = _homePageUiState.asStateFlow()

    private val _homePageUiEvent = MutableSharedFlow<HomeUiEvent>()
    val homePageUiEvent get() = _homePageUiEvent.asSharedFlow()

    fun getAllHomePageData(
        onLocationClick: () -> Unit,
    ) {
        viewModelScope.launch {
            val homePage = repository.getAllHomePageData()
            generateUiItems(homePage, onLocationClick)
        }
    }

    fun getHomePageDataByLocation(
        longitude: Double,
        latitude: Double,
        onLocationClick: () -> Unit,
    ) {
        viewModelScope.launch {
            val homePageUiModel = repository.getHomePageDataByLocation(
                longitude = longitude,
                latitude = latitude
            )

            generateUiItems(homePageUiModel, onLocationClick)
        }
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
            _homePageUiEvent.emit(HomeUiEvent.NavigateToSalonDetail(salonId))
        }
    }
}
