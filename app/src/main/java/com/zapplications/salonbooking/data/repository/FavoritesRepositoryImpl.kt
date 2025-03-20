package com.zapplications.salonbooking.data.repository

import com.zapplications.salonbooking.data.datasource.local.FavoritesLocalDataSource
import com.zapplications.salonbooking.data.local.entity.FavoriteSalonEntity
import com.zapplications.salonbooking.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val favoritesLocalDataSource: FavoritesLocalDataSource
) : FavoritesRepository {
    override fun getAllFavoriteSalons(): Flow<List<FavoriteSalonEntity>> {
        return favoritesLocalDataSource.getAllFavoriteSalons()
    }

    override suspend fun getFavoriteSalonById(salonId: String): FavoriteSalonEntity? {
        return favoritesLocalDataSource.getFavoriteSalonById(salonId)
    }

    override suspend fun insertFavorite(favorite: FavoriteSalonEntity): Result<Unit> {
        return favoritesLocalDataSource.insertFavorite(favorite)
    }

    override suspend fun deleteFavorite(id: String): Result<Unit> {
        return favoritesLocalDataSource.deleteFavorite(id)
    }
}
