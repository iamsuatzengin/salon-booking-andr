package com.zapplications.salonbooking.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zapplications.salonbooking.data.local.dao.FavoritesDao
import com.zapplications.salonbooking.data.local.entity.FavoriteSalonEntity

@Database(
    entities = [FavoriteSalonEntity::class],
    version = 1
)
abstract class SalonBookingLocalDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
}
