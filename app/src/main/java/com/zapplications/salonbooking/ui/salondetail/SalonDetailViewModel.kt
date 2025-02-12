package com.zapplications.salonbooking.ui.salondetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zapplications.salonbooking.domain.model.SalonUiModel
import com.zapplications.salonbooking.domain.repository.SalonDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SalonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: SalonDetailRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<SalonUiModel?>(null)
    val uiState get() = _uiState.asStateFlow()

    private val salonId = savedStateHandle.get<String>(SALON_ID).orEmpty()

    fun getSalonDetail() {
        viewModelScope.launch {
            val salonDetailUiModel = repository.getSalonDetail(salonId)

            _uiState.update { salonDetailUiModel }
        }
    }

    companion object {
        const val SALON_ID = "salonId"
    }
}
