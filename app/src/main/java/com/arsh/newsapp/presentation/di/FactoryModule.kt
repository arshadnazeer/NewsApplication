package com.arsh.newsapp.presentation.di

import android.app.Application
import com.arsh.newsapp.domain.usecase.*
import com.arsh.newsapp.presentation.viewmodel.NewsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
    fun providesNewsViewModelFactory(
        app : Application,
        getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
        getSearchedNewsUseCase: GetSearchedNewsUseCase,
        saveNewsUseCase: SaveNewsUseCase,
        getSavedNewsUseCase: GetSavedNewsUseCase,
        deleteSavedNewsUseCase: DeleteSavedNewsUseCase
    ) : NewsViewModelFactory{
        return NewsViewModelFactory(app,getTopHeadlinesUseCase,getSearchedNewsUseCase,
            saveNewsUseCase,getSavedNewsUseCase,deleteSavedNewsUseCase)
    }
}