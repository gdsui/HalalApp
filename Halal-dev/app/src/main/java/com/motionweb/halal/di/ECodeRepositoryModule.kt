package com.motionweb.halal.di

import com.motionweb.halal.repository.ECodeRepository
import com.motionweb.halal.repository.ECodeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ECodeRepositoryModule {

    @Binds
    abstract fun bindECodeRepository(repositoryImpl: ECodeRepositoryImpl): ECodeRepository

}