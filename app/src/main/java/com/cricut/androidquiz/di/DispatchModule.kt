package com.cricut.androidquiz.di

import com.cricut.common.provider.DispatchProvider
import com.cricut.common.provider.DispatcherProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatchModule {

    @Provides
    @Singleton
    fun provideDispatchProvider(): DispatchProvider = DispatcherProviderImpl()
}