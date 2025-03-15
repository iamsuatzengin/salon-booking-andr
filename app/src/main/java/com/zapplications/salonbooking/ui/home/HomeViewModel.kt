package com.zapplications.salonbooking.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.domain.repository.HomeRepository
import com.zapplications.salonbooking.ui.home.adapter.item.BannerViewItem
import com.zapplications.salonbooking.core.adapter.Item
import com.zapplications.salonbooking.core.adapter.commonview.emptystate.EmptyStateViewItem
import com.zapplications.salonbooking.domain.model.HomePageUiModel
import com.zapplications.salonbooking.ui.home.adapter.item.NearbySalonViewItem
import com.zapplications.salonbooking.ui.home.adapter.item.SearchViewItem
import com.zapplications.salonbooking.ui.home.adapter.item.ServiceCategoryViewItem
import com.zapplications.salonbooking.ui.home.adapter.item.TitleViewItem
import com.zapplications.salonbooking.ui.home.adapter.item.TopViewItem
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
            generateHomeUiItems(homePage, onLocationClick)
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

            generateHomeUiItems(homePageUiModel, onLocationClick)
        }
    }

    private fun generateHomeUiItems(uiModel: HomePageUiModel?, onLocationClick: () -> Unit) {
        val recyclerItems: MutableList<Item> = mutableListOf(
            TopViewItem(
                title = "Location",
                locationString = _homePageUiState.value.locationString,
                clickHandler = onLocationClick
            ),
            SearchViewItem(hint = "Enter address or city name"),
        )
        uiModel?.banner?.let { recyclerItems.add(BannerViewItem(bannerUiModel = it)) }
        uiModel?.categories?.let { categories ->
            recyclerItems.add(TitleViewItem(title = "Services"))
            recyclerItems.add(ServiceCategoryViewItem(
                categories,
                onCategoryClick = {
                    Log.i("HomeViewModel", "Category clicked: $it")
                }
            ))
        }
        recyclerItems.add(
            TitleViewItem(
                title = "Nearby Salons",
                actionText = "View on map",
                actionClickHandler = {
                    Log.i("HomeViewModel", "Action text clicked")
                }
            )
        )

        if (uiModel?.salons == null || uiModel.salons.isEmpty()) {
            recyclerItems.add(
                EmptyStateViewItem(
                    R.drawable.ic_salon_empty_state,
                    title = "Salons not found!",
                    body = "No salons found near you. Try again later."
                )
            )
        } else {
            val model = uiModel.salons.mapNotNull { salon ->
                salon?.let {
                    NearbySalonViewItem(salonUiModel = salon, clickHandler = { salonId ->
                        navigateToSalonDetail(salonId)
                    })
                }
            }
            recyclerItems.addAll(model)
        }

        _homePageUiState.update { it.copy(homePageUiModel = recyclerItems) }
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
