package com.motionweb.halal.di

import com.motionweb.halal.repository.LoginRepository
import com.motionweb.halal.repository.LoginRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class LoginRepositoryModule {

    @Binds
    abstract fun bindLoginRepo(impl: LoginRepositoryImpl): LoginRepository

}