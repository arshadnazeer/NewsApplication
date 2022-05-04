package com.arsh.newsapp.domain.usecase

import com.arsh.newsapp.data.model.Article
import com.arsh.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetSavedNewsUseCase(
    private val newsRepository: NewsRepository
) {
    fun execute() : Flow<List<Article>>{
        return newsRepository.getSavedNewsUseCase()
    }
}