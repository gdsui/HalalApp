package com.motionweb.halal.di

import com.motionweb.halal.repository.QuestionsRepository
import com.motionweb.halal.repository.QuestionsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class QuestionRepositoryModule {

    @Binds
    abstract fun bindQuestionRepository(questionRepo: QuestionsRepositoryImpl): QuestionsRepository
}