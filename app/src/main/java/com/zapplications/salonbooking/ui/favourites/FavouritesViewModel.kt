package com.zapplications.salonbooking.ui.favourites

import androidx.lifecycle.viewModelScope
import com.zapplications.salonbooking.core.ui.BaseViewModel
import com.zapplications.salonbooking.data.local.entity.FavoriteSalonEntity
import com.zapplications.salonbooking.domain.repository.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val repository: FavoritesRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow<List<FavoriteSalonEntity>>(emptyList())
    val uiState: StateFlow<List<FavoriteSalonEntity>> = _uiState.asStateFlow()

    fun getAllFavoriteSalonsFromLocal() {
        viewModelScope.launch {
            repository.getAllFavoriteSalons().collect { list ->
                _uiState.update { list }
            }
        }
    }
}
