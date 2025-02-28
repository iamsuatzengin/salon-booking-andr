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
import com.zapplications.salonbooking.domain.model.datetime.DateUiModel
import com.zapplications.salonbooking.ui.datetimeselection.adapter.item.SelectTimeViewItem
import com.zapplications.salonbooking.ui.home.adapter.item.TitleViewItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DateTimeSelectionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: SalonDetailRepository,
    private val getThreeDateFromNowUseCase: GetThreeDateFromNowUseCase,
    private val getWorkingHoursUseCase: GetWorkingHoursUseCase,
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<DateTimeSelectionUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val stylistId = savedStateHandle.get<String>(STYLIST_ID_KEY)

    private var selectedPosition: Int? = null
    private var selectedTime: SelectTimeViewItem? = null
    private var selectedDate: DateUiModel? = null

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
                selectedDate = selectedDate,
                clickHandler = { item, viewItem, position ->
                    selectedDate = item
                    viewItem.selectedDate = selectedDate
                    viewModelScope.launch {
                        _uiEvent.emit(DateTimeSelectionUiEvent.SelectDate(position))
                    }
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
                clickHandler = ::handleSelectTime
            )
        }.also { list.addAll(it) }
    }

    private fun handleSelectTime(item: SelectTimeViewItem, position: Int) {
        val previousPosition = selectedPosition
        selectedTime?.let { it.isSelected = false }
        selectedTime = item
        selectedPosition = position
        selectedTime?.isSelected = true
        viewModelScope.launch {
            _uiEvent.emit(DateTimeSelectionUiEvent.SelectTime(previousPosition, selectedPosition))
        }
    }

    companion object {
        const val STYLIST_ID_KEY = "stylistId"
    }
}
