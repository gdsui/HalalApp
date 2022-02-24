package com.motionweb.halal.di

import com.motionweb.halal.repository.MainActivityRepository
import com.motionweb.halal.repository.MainActivityRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class MainActivityRepositoryModule {

    @Binds
    abstract fun bindMainActivityRepository(repository: MainActivityRepositoryImpl): MainActivityRepository

}