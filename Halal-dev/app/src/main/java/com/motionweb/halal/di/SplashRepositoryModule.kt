package com.motionweb.halal.di

import com.motionweb.halal.repository.SplashRepository
import com.motionweb.halal.repository.SplashRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class SplashRepositoryModule {

    @Binds
    abstract fun bindSplashRepository(splashRepositoryImpl: SplashRepositoryImpl): SplashRepository

}