package com.zapplications.salonbooking.di

import com.zapplications.salonbooking.data.repository.AuthRepositoryImpl
import com.zapplications.salonbooking.data.repository.BookingRepositoryImpl
import com.zapplications.salonbooking.data.repository.HomeRepositoryImpl
import com.zapplications.salonbooking.data.repository.SalonDetailRepositoryImpl
import com.zapplications.salonbooking.data.repository.SettingsRepositoryImpl
import com.zapplications.salonbooking.domain.repository.AuthRepository
import com.zapplications.salonbooking.domain.repository.BookingRepository
import com.zapplications.salonbooking.domain.repository.HomeRepository
import com.zapplications.salonbooking.domain.repository.SalonDetailRepository
import com.zapplications.salonbooking.domain.repository.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    abstract fun bindHomeRepository(
        homeRepositoryImpl: HomeRepositoryImpl
    ): HomeRepository

    @Binds
    abstract fun bindSalonDetailRepository(
        salonDetailRepositoryImpl: SalonDetailRepositoryImpl
    ): SalonDetailRepository

    @Binds
    abstract fun bindBookingRepository(
        bookingRepositoryImpl: BookingRepositoryImpl
    ): BookingRepository

    @Binds
    abstract fun bindSettingsRepository(
        settingsRepositoryImpl: SettingsRepositoryImpl
    ): SettingsRepository
}
