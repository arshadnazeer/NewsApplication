package com.arsh.newsapp.presentation.di

import com.arsh.newsapp.data.api.NewsAPIService
import com.arsh.newsapp.data.repository.datasource.NewsRemoteDataSource
import com.arsh.newsapp.data.repository.datasourceImpl.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {

    @Singleton
    @Provides
    fun providesRemoteDataSource(newsAPIService: NewsAPIService) : NewsRemoteDataSource{
        return NewsRemoteDataSourceImpl(newsAPIService)
    }
}