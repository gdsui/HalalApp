package com.motionweb.halal.di

import com.motionweb.halal.repository.PrayerTimeRepository
import com.motionweb.halal.repository.PrayerTimeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class PrayerTimeRepositoryModule {

    @Binds
    abstract fun bindPrayerTimeRepository(repositoryImpl: PrayerTimeRepositoryImpl): PrayerTimeRepository

}