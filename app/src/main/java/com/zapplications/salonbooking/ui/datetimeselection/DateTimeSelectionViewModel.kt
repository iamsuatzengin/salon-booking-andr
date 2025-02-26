package com.zapplications.salonbooking.ui.datetimeselection

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zapplications.salonbooking.domain.repository.SalonDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DateTimeSelectionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: SalonDetailRepository
) : ViewModel() {

    private val stylistId = savedStateHandle.get<String>(STYLIST_ID_KEY)

    fun getStylistAvailability(date: String) {
        viewModelScope.launch {
            stylistId?.let { repository.getStylistAvailability(it, date) }
        }
    }

    companion object {
        const val STYLIST_ID_KEY = "stylistId"
    }
}