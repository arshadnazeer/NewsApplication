package com.arsh.newsapp.domain.usecase

import com.arsh.newsapp.data.model.Article
import com.arsh.newsapp.domain.repository.NewsRepository

class DeleteSavedNewsUseCase (
    private val newsRepository: NewsRepository
        ){
    suspend fun execute(article: Article){
        return newsRepository.deleteSavedNewsUseCase(article)
    }
}