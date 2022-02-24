package com.motionweb.halal.di.onboarding

import com.motionweb.halal.repository.SelectionRepository
import com.motionweb.halal.repository.SelectionRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class SelectionRepositoryModule {

    @Binds
    abstract fun bindSelectionRepositoryModule(selectionRepositoryImpl: SelectionRepositoryImpl): SelectionRepository

}