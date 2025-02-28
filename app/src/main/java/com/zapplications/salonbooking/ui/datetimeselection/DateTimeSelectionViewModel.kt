package com.zapplications.salonbooking.ui.datetimeselection

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zapplications.salonbooking.domain.repository.SalonDetailRepository
import com.zapplications.salonbooking.domain.usecase.GetThreeDateFromNowUseCase
import com.zapplications.salonbooking.domain.usecase.GetWorkingHoursUseCase
import com.zapplications.salonbooking.ui.datetimeselection.adapter.item.SelectDateViewItem
import com.zapplications.salonbooking.core.adapter.Item
import com.zapplications.salonbooking.ui.datetimeselection.adapter.item.SelectTimeViewItem
import com.zapplications.salonbooking.ui.home.adapter.item.TitleViewItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DateTimeSelectionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: SalonDetailRepository,
    private val getThreeDateFromNowUseCase: GetThreeDateFromNowUseCase,
    private val getWorkingHoursUseCase: GetWorkingHoursUseCase
) : ViewModel() {

    private val stylistId = savedStateHandle.get<String>(STYLIST_ID_KEY)

    fun getStylistAvailability(date: String) {
        viewModelScope.launch {
            stylistId?.let {
                val availability = repository.getStylistAvailability(it, date)
                Log.i("DateTimeSelectionViewModel", "availability: $availability")
            }
        }
    }

    val list = mutableListOf<Item>()
    fun generateUiAdapter() {
        list.add(TitleViewItem("Select Date"))
        list.add(
            SelectDateViewItem(
                dateUiModel = getThreeDateFromNowUseCase(),
                clickHandler = {
                    Log.i("DateTimeSelectionViewModel", "clickHandler is called: $it")
                },
                moreClickHandler = {
                    Log.i("DateTimeSelectionViewModel", "moreClickHandler is called")
                }
            )
        )
        list.add(TitleViewItem("Select Time"))
        getWorkingHoursUseCase().map { timeUiModel ->
            SelectTimeViewItem(
                timeUiModel = timeUiModel,
                clickHandler = {
                    Log.i("DateTimeSelectionViewModel", "Time - clickHandler is called: $it")
                }
            )
        }.also { list.addAll(it) }
    }

    companion object {
        const val STYLIST_ID_KEY = "stylistId"
    }
}