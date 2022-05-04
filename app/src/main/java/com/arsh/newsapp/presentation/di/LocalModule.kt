package com.arsh.newsapp.presentation.di

import com.arsh.newsapp.data.db.ArticleDAO
import com.arsh.newsapp.data.repository.datasource.NewsLocalDataSource
import com.arsh.newsapp.data.repository.datasourceImpl.NewsLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    @Singleton
    @Provides
    fun providesNewsLocalDataSource(articleDAO: ArticleDAO) : NewsLocalDataSource{
        return NewsLocalDataSourceImpl(articleDAO)
    }
}