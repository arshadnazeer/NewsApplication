package com.arsh.newsapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arsh.newsapp.domain.usecase.*

class NewsViewModelFactory(
    private val app: Application,
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase,
    private val saveNewsUseCase: SaveNewsUseCase,
    private val getSavedNewsUseCase: GetSavedNewsUseCase,
    private val deleteSavedNewsUseCase: DeleteSavedNewsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(
            app,getTopHeadlinesUseCase, getSearchedNewsUseCase,saveNewsUseCase,
            getSavedNewsUseCase, deleteSavedNewsUseCase) as T
    }
}