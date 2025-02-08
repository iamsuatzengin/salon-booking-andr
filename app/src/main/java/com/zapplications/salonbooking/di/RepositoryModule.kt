package com.zapplications.salonbooking.di

import com.zapplications.salonbooking.data.repository.AuthRepositoryImpl
import com.zapplications.salonbooking.data.repository.HomeRepositoryImpl
import com.zapplications.salonbooking.domain.repository.AuthRepository
import com.zapplications.salonbooking.domain.repository.HomeRepository
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
}
