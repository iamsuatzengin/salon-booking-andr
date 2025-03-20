package com.zapplications.salonbooking.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zapplications.salonbooking.data.local.entity.FavoriteSalonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM favorites")
    fun getAllFavoriteSalons(): Flow<List<FavoriteSalonEntity>>

    @Query("SELECT * FROM favorites WHERE id = :salonId")
    suspend fun getFavoriteSalonById(salonId: String): FavoriteSalonEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteSalonEntity)

    @Query("DELETE FROM favorites WHERE id = :id")
    suspend fun deleteFavorite(id: String)
}
