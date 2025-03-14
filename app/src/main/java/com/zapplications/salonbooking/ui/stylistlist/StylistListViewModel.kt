package com.zapplications.salonbooking.ui.stylistlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zapplications.salonbooking.core.adapter.Item
import com.zapplications.salonbooking.domain.repository.SalonDetailRepository
import com.zapplications.salonbooking.ui.salondetail.SalonDetailViewModel.Companion.SALON_ID
import com.zapplications.salonbooking.ui.stylistlist.adapter.item.AnyStylistViewItem
import com.zapplications.salonbooking.ui.stylistlist.adapter.item.StylistViewItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StylistListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: SalonDetailRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(StylistListUiState())
    val uiState get() = _uiState.asStateFlow()

    var selectedStylist: Item? = null
        private set
    private var selectedPosition: Int? = null

    val salonId = savedStateHandle.get<String>(SALON_ID).orEmpty()

    fun getStylistsBySalonId() {
        viewModelScope.launch {
            val stylists = uiState.value.uiItems.toMutableList()

            if (stylists.isNotEmpty()) return@launch

            val response = repository.getStylistsBySalonId(salonId).orEmpty()
            stylists.add(
                AnyStylistViewItem(onClick = { item, pos -> selectStylist(item, pos) })
            )
            stylists.addAll(
                response.map {
                    StylistViewItem(
                        stylistUiModel = it,
                        onClick = { item, pos -> selectStylist(item, pos) }
                    )
                }
            )
            _uiState.update { it.copy(uiItems = stylists) }
        }
    }

    private fun selectStylist(stylistUiModel: Item, position: Int) {
        val previousPosition = selectedPosition
        selectedPosition = position

        val stylistList = uiState.value.uiItems.toMutableList()

        previousPosition?.let { pos ->
            (stylistList.getOrNull(pos) as? AnyStylistViewItem)?.let {
                stylistList.set(pos, it.copy(isSelected = false))
            }

            (stylistList.getOrNull(pos) as? StylistViewItem)?.let {
                stylistList.set(pos, it.copy(isSelected = false))
            }
        }

        selectedStylist = stylistUiModel.let { stylist ->
            when (stylist) {
                is AnyStylistViewItem -> {
                    stylist.copy(isSelected = true)
                }

                is StylistViewItem -> {
                    stylist.copy(isSelected = true)
                }

                else -> null
            }
        }
        selectedStylist?.let {
            stylistList[position] = it
        }
        _uiState.update {
            it.copy(
                uiItems = stylistList.toList(),
                buttonEnabled = stylistList.any { item ->
                    (item is StylistViewItem && item.isSelected) || (item is AnyStylistViewItem && item.isSelected)
                }
            )
        }
    }
}

data class StylistListUiState(
    val uiItems: List<Item> = emptyList(),
    val buttonEnabled: Boolean = false,
)