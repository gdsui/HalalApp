package com.motionweb.halal.di

import com.motionweb.halal.repository.RequestsRepository
import com.motionweb.halal.repository.RequestsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RequestRepositoryModule {

    @Binds
    abstract fun bindRequestRepository(impl: RequestsRepositoryImpl): RequestsRepository
}