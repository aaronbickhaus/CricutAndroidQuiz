package com.cricut.data.di

import com.cricut.data.repository.FetchFourQuestionsRepository
import com.cricut.data.repository.FetchFourQuestionsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindQuestionRepository(impl: FetchFourQuestionsRepositoryImpl): FetchFourQuestionsRepository
}