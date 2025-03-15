package com.zapplications.salonbooking.domain.usecase.itemusecases

import android.util.Log
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.adapter.Item
import com.zapplications.salonbooking.core.adapter.commonview.emptystate.EmptyStateViewItem
import com.zapplications.salonbooking.domain.model.HomePageUiModel
import com.zapplications.salonbooking.domain.model.SalonUiModel
import com.zapplications.salonbooking.ui.home.adapter.item.BannerViewItem
import com.zapplications.salonbooking.ui.home.adapter.item.NearbySalonViewItem
import com.zapplications.salonbooking.ui.home.adapter.item.SearchViewItem
import com.zapplications.salonbooking.ui.home.adapter.item.ServiceCategoryViewItem
import com.zapplications.salonbooking.ui.home.adapter.item.TitleViewItem
import com.zapplications.salonbooking.ui.home.adapter.item.TopViewItem
import javax.inject.Inject

/**
 * `GenerateHomeUiItems` is a class responsible for generating a list of UI items
 * for the home screen of the application. It takes data from the `HomePageUiModel`
 * and converts it into a list of `Item` objects that can be rendered by a RecyclerView.
 *
 * We create an instance of this class in the `HomeViewModel` and use it to generate the
 * multi type recycler view items.
 * @see com.zapplications.salonbooking.ui.home.adapter.HomeAdapter
 *
 * TODO: Strings should be refactored for supporting localization.
 */
class GenerateHomeUiItems @Inject constructor() {
    operator fun invoke(
        uiModel: HomePageUiModel?,
        locationString: String,
        onLocationClick: () -> Unit,
        navigateToSalonDetail: (salonId: String) -> Unit
    ): List<Item> = mutableListOf<Item>().apply {
        add(
            TopViewItem(
                title = "Location",
                locationString = locationString,
                clickHandler = onLocationClick
            )
        )
        add(SearchViewItem(hint = "Enter address or city name"))
        uiModel?.banner?.let { add(BannerViewItem(bannerUiModel = it)) }
        uiModel?.categories?.let { categories ->
            add(TitleViewItem(title = "Services"))
            add(
                ServiceCategoryViewItem(
                    categories,
                    onCategoryClick = {
                        Log.i("HomeViewModel", "Category clicked: $it")
                    }
                )
            )
        }
        add(
            TitleViewItem(
                title = "Nearby Salons",
                actionText = "View on map",
                actionClickHandler = {
                    Log.i("HomeViewModel", "Action text clicked")
                }
            )
        )
        generateSalonItemOrEmptyState(uiModel?.salons, navigateToSalonDetail)
    }

    private fun MutableList<Item>.generateSalonItemOrEmptyState(
        salons: List<SalonUiModel?>?,
        navigateToSalonDetail: (String) -> Unit
    ) {
        if (salons.isNullOrEmpty()) {
            add(
                EmptyStateViewItem(
                    imageRes = R.drawable.ic_salon_empty_state,
                    title = "Salons not found!",
                    body = "No salons found near you. Try again later."
                )
            )
        } else {
            val model = salons.mapNotNull { salon ->
                salon?.let {
                    NearbySalonViewItem(
                        salonUiModel = salon,
                        clickHandler = { salonId ->
                            navigateToSalonDetail(salonId)
                        }
                    )
                }
            }
            addAll(model)
        }
    }
}