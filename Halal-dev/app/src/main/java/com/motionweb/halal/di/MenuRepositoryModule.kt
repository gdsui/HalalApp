package com.motionweb.halal.di

import com.motionweb.halal.repository.MenuRepository
import com.motionweb.halal.repository.MenuRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class MenuRepositoryModule {

    @Binds
    abstract fun bindMenuRepository(repositoryImpl: MenuRepositoryImpl): MenuRepository

}