package com.motionweb.halal.di

import com.motionweb.halal.repository.CatalogRepository
import com.motionweb.halal.repository.CatalogRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class CatalogRepositoryModule {

    @Binds
    abstract fun bindCatalogRepository(repositoryImpl: CatalogRepositoryImpl): CatalogRepository

}