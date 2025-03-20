package com.zapplications.salonbooking.data.datasource.local

import android.util.Log
import com.zapplications.salonbooking.data.local.dao.FavoritesDao
import com.zapplications.salonbooking.data.local.entity.FavoriteSalonEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoritesLocalDataSource @Inject constructor(
    private val favoritesDao: FavoritesDao,
) {

    fun getAllFavoriteSalons(): Flow<List<FavoriteSalonEntity>> =
        favoritesDao.getAllFavoriteSalons()
            .onStart {
                emit(emptyList())
            }.catch {
                Log.e("FavoritesLocalDataSource", "getAllFavorites: $it")
                emit(emptyList())
            }.flowOn(Dispatchers.IO)

    suspend fun getFavoriteSalonById(salonId: String): FavoriteSalonEntity? = withContext(Dispatchers.IO) {
        runCatching {
            favoritesDao.getFavoriteSalonById(salonId)
        }.getOrNull()
    }

    suspend fun insertFavorite(favorite: FavoriteSalonEntity) = withContext(Dispatchers.IO) {
        runCatching {
            favoritesDao.insertFavorite(favorite)
        }
    }

    suspend fun deleteFavorite(id: String) = withContext(Dispatchers.IO) {
        runCatching {
            favoritesDao.deleteFavorite(id)
        }
    }
}