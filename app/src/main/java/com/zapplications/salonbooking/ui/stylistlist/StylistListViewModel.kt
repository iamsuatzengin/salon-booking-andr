package com.zapplications.salonbooking.ui.stylistlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zapplications.salonbooking.domain.model.StylistUiModel
import com.zapplications.salonbooking.domain.model.anyStylistItem
import com.zapplications.salonbooking.domain.repository.SalonDetailRepository
import com.zapplications.salonbooking.ui.salondetail.SalonDetailViewModel.Companion.SALON_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StylistListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: SalonDetailRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<List<StylistUiModel>>(emptyList())
    val uiState get() = _uiState.asStateFlow()

    val salonId = savedStateHandle.get<String>(SALON_ID).orEmpty()

    fun getStylistsBySalonId() {
        viewModelScope.launch {
            val stylists = arrayListOf<StylistUiModel>()
            stylists.add(anyStylistItem())
            stylists.addAll(repository.getStylistsBySalonId(salonId).orEmpty())
            _uiState.update { stylists }
        }
    }
}