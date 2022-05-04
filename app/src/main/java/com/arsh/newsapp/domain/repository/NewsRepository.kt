package com.arsh.newsapp.domain.repository

import com.arsh.newsapp.data.model.Article
import com.arsh.newsapp.data.model.NewsAPIResponse
import com.arsh.newsapp.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getTopNewsHeadlinesUseCase(country: String, page : Int) : Resource<NewsAPIResponse>
    suspend fun getSearchedNewsUseCase(country: String, page: Int, searchQuery : String) : Resource<NewsAPIResponse>
    suspend fun saveNewsUseCase(article: Article)
    fun getSavedNewsUseCase() : Flow<List<Article>>
    suspend fun deleteSavedNewsUseCase(article: Article)
}