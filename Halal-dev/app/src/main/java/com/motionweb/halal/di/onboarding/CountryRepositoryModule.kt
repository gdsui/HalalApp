package com.motionweb.halal.di.onboarding

import com.motionweb.halal.repository.CountryRepository
import com.motionweb.halal.repository.CountryRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CountryRepositoryModule {

    @Binds
    abstract fun bindCountryRepository(countryRepositoryImpl: CountryRepositoryImpl): CountryRepository

}