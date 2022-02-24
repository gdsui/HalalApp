package com.motionweb.halal.di.onboarding

import com.motionweb.halal.repository.LanguageRepository
import com.motionweb.halal.repository.LanguageRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class LanguageRepositoryModule {

    @Binds
    abstract fun bindLanguageRepository(languageRepositoryImpl: LanguageRepositoryImpl): LanguageRepository

}