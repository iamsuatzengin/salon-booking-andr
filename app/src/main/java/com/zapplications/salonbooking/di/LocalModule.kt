package com.zapplications.salonbooking.di

import android.content.Context
import androidx.room.Room
import com.zapplications.salonbooking.data.local.SalonBookingLocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

const val DATABASE_NAME = "salon_booking_db"

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    fun provideSalonBookingLocalDatabase(
        @ApplicationContext context: Context
    ): SalonBookingLocalDatabase = Room.databaseBuilder(
        context = context,
        klass = SalonBookingLocalDatabase::class.java,
        name = DATABASE_NAME
    ).build()

    @Provides
    fun provideFavoritesDao(
        db: SalonBookingLocalDatabase
    ) = db.favoritesDao()
}
