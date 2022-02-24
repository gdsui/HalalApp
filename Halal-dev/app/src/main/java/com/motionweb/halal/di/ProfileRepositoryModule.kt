package com.motionweb.halal.di

import com.motionweb.halal.repository.ProfileRepository
import com.motionweb.halal.repository.ProfileRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ProfileRepositoryModule {

    @Binds
    abstract fun bindProfileRepository(repositoryImpl: ProfileRepositoryImpl): ProfileRepository

}