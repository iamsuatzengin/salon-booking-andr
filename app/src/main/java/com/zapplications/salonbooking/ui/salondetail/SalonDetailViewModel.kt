package com.zapplications.salonbooking.ui.salondetail

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.data.local.entity.FavoriteSalonEntity
import com.zapplications.salonbooking.domain.repository.FavoritesRepository
import com.zapplications.salonbooking.domain.repository.SalonDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SalonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: SalonDetailRepository,
    private val favoriteRepository: FavoritesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SalonDetailUiState())
    val uiState get() = _uiState.asStateFlow()

    val salonId = savedStateHandle.get<String>(SALON_ID).orEmpty()

    var isFavoriteSelected: Boolean = false

    @get:DrawableRes
    val favoriteIcon: Int
        get() = if (isFavoriteSelected) {
            R.drawable.ic_favorite_filled
        } else {
            R.drawable.ic_favorite_outlined
        }

    fun getSalonDetail() {
        viewModelScope.launch {
            val isFavorite = async{ favoriteRepository.getFavoriteSalonById(salonId) != null }
            val salonDetailUiModel = async{ repository.getSalonDetail(salonId) }

            _uiState.update {
                it.copy(
                    salonUiModel = salonDetailUiModel.await(),
                    isSalonFavorite = isFavorite.await()
                )
            }
        }
    }

    fun handleFavoriteIconClick() {
        isFavoriteSelected = !isFavoriteSelected
        if (isFavoriteSelected) addToFavorites() else deleteFromFavorites()
    }

    private fun addToFavorites() {
        viewModelScope.launch {
            val salonUiModel = _uiState.value.salonUiModel ?: return@launch
            val model = FavoriteSalonEntity(
                id = salonUiModel.id,
                salonName = salonUiModel.salonName,
                rating = salonUiModel.rating,
                address = salonUiModel.address,
                reviewerCount = salonUiModel.reviewerCount,
                imageUrl = salonUiModel.imageUrl
            )
            favoriteRepository.insertFavorite(model)
                .onSuccess {
                    Log.d("SalonDetailViewModel", "SUCCESS - addToFavorites: $it")
                }
                .onFailure {
                    Log.e("SalonDetailViewModel", "FAILURE - addToFavorites: $it")
                }
        }
    }

    private fun deleteFromFavorites() {
        viewModelScope.launch {
            favoriteRepository.deleteFavorite(salonId)
                .onSuccess {
                    Log.d("SalonDetailViewModel", "SUCCESS - deleteFromFavorites: $it")
                }
                .onFailure {
                    Log.e("SalonDetailViewModel", "FAILURE - deleteFromFavorites: $it")
                }
        }
    }

    companion object {
        const val SALON_ID = "salonId"
    }
}
