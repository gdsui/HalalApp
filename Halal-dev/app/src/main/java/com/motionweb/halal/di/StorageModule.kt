package com.motionweb.halal.di

import android.content.Context
import com.motionweb.halal.data.storage.db.FavoriteDao
import com.motionweb.halal.data.storage.db.HalalDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    @Provides
    fun provideDB(@ApplicationContext context: Context): HalalDB =
        HalalDB.getInstance(context)

    @Provides
    fun provideFavoriteDao(db: HalalDB): FavoriteDao =
        db.favoriteDao()

}