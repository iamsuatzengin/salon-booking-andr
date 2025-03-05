package com.zapplications.salonbooking.ui.datetimeselection

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zapplications.salonbooking.core.adapter.Item
import com.zapplications.salonbooking.core.extensions.orTrue
import com.zapplications.salonbooking.domain.model.StylistAvailabilityUiModel
import com.zapplications.salonbooking.domain.model.datetime.DateUiModel
import com.zapplications.salonbooking.domain.repository.SalonDetailRepository
import com.zapplications.salonbooking.domain.usecase.GetThreeDateFromNowUseCase
import com.zapplications.salonbooking.domain.usecase.GetWorkingHoursUseCase
import com.zapplications.salonbooking.ui.datetimeselection.adapter.item.SelectDateViewItem
import com.zapplications.salonbooking.ui.datetimeselection.adapter.item.SelectTimeViewItem
import com.zapplications.salonbooking.ui.home.adapter.item.TitleViewItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DateTimeSelectionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: SalonDetailRepository,
    private val getThreeDateFromNowUseCase: GetThreeDateFromNowUseCase,
    private val getWorkingHoursUseCase: GetWorkingHoursUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DateTimeSelectionUiState())
    val uiState get() = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<DateTimeSelectionUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val stylistId = savedStateHandle.get<String>(STYLIST_ID_KEY)

    private val uiItems = mutableListOf<Item>()
    private var selectedPosition: Int? = null
    private var selectedTime: SelectTimeViewItem? = null
    private var selectedDate: DateUiModel? = null

    private fun getStylistAvailability(date: String) {
        if (stylistId.isNullOrEmpty()) return

        viewModelScope.launch {
            val availability = repository.getStylistAvailability(stylistId, date) ?: return@launch

            uiItems.removeAll { item -> item is SelectTimeViewItem }
            generateTimes(availability).also { t -> uiItems.addAll(t) }

            _uiState.update {
                it.copy(
                    items = uiItems.toList(),
                    isConfirmButtonEnabled = handleConfirmButtonEnabledState()
                )
            }
        }
    }

    fun generateUiAdapter(availability: StylistAvailabilityUiModel? = null) {
        if (uiItems.isNotEmpty()) return

        uiItems.apply {
            add(TitleViewItem("Select Date"))

            add(
                SelectDateViewItem(
                    dateUiModel = getThreeDateFromNowUseCase(),
                    selectedDate = selectedDate,
                    clickHandler = ::handleSelectDate,
                    moreClickHandler = {
                        Log.i("DateTimeSelectionViewModel", "moreClickHandler is called")
                    }
                )
            )

            add(TitleViewItem("Select Time"))
        }

        generateTimes(availability).also { uiItems.addAll(it) }
        _uiState.update {
            it.copy(
                items = uiItems.toList(),
                isConfirmButtonEnabled = handleConfirmButtonEnabledState()
            )
        }
    }

    private fun generateTimes(availability: StylistAvailabilityUiModel?) =
        getWorkingHoursUseCase().map { timeUiModel ->
            val isNotAvailable = availability?.availability?.contains(timeUiModel.time.toString())
                ?.not()
            SelectTimeViewItem(
                timeUiModel = timeUiModel,
                isSelected = false,
                isAvailable = if (selectedDate == null) false else isNotAvailable.orTrue(),
                clickHandler = ::handleSelectTime
            )
        }

    private fun handleSelectDate(
        date: DateUiModel,
        viewItem: SelectDateViewItem,
        rowPosition: Int,
    ) {
        runCatching {
            val new = viewItem.copy(selectedDate = date)
            uiItems[rowPosition] = new
            selectedDate = date
            selectedTime = null
            selectedPosition = null
            getStylistAvailability(date.formattedDate)
        }
    }

    private fun handleSelectTime(item: SelectTimeViewItem, position: Int) {
        val previousPosition = selectedPosition
        selectedTime = selectedTime?.copy(isSelected = false)
        previousPosition?.let { pos ->
            uiItems.updateSelectedTime(position = pos, isSelected = false)
        }

        selectedTime = item.copy(isSelected = true)
        selectedPosition = position

        uiItems.updateSelectedTime(position = position, isSelected = true)

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    items = uiItems.toList(),
                    isConfirmButtonEnabled = handleConfirmButtonEnabledState()
                )
            }
        }
    }

    private fun MutableList<Item>.updateSelectedTime(
        position: Int, isSelected: Boolean,
    ) = runCatching {
        val item = (get(position) as? SelectTimeViewItem)
        this[position] = item?.copy(isSelected = isSelected) ?: return@runCatching
    }.onFailure { throwable ->
        Log.e(TAG, "onFailure: $throwable")
    }.getOrNull()

    private fun handleConfirmButtonEnabledState() = selectedDate != null && selectedTime != null

    companion object {
        const val STYLIST_ID_KEY = "stylistId"
        private const val TAG = "DateTimeSelectionViewModel"
    }
}
