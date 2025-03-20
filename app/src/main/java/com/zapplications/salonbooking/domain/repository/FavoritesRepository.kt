package com.zapplications.salonbooking.domain.repository

import com.zapplications.salonbooking.data.local.entity.FavoriteSalonEntity
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun getAllFavoriteSalons(): Flow<List<FavoriteSalonEntity>>
    suspend fun getFavoriteSalonById(salonId: String): FavoriteSalonEntity?
    suspend fun insertFavorite(favorite: FavoriteSalonEntity): Result<Unit>
    suspend fun deleteFavorite(id: String): Result<Unit>
}
