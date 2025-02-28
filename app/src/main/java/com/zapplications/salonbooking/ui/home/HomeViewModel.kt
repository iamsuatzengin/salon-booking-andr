package com.zapplications.salonbooking.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zapplications.salonbooking.domain.repository.HomeRepository
import com.zapplications.salonbooking.ui.home.adapter.item.BannerViewItem
import com.zapplications.salonbooking.core.adapter.Item
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
            val recyclerItems: MutableList<Item> = mutableListOf(
                TopViewItem(
                    title = "Location",
                    locationString = homePageUiState.value.locationString,
                    clickHandler = onLocationClick
                ),
                SearchViewItem(hint = "Enter address or city name"),
            )
            homePage?.banner?.let { recyclerItems.add(BannerViewItem(bannerUiModel = it)) }
            homePage?.categories?.let { categories ->
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
            homePage?.salons?.let {
                val model = it.mapNotNull { salon ->
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
