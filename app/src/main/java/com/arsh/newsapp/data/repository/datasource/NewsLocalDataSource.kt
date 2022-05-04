package com.arsh.newsapp.data.repository.datasource

import com.arsh.newsapp.data.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsLocalDataSource {
    suspend fun saveNewsArticle(article: Article)
    fun getSavedArticlesFromDB() : Flow<List<Article>>
    suspend fun deleteSavedArticleFromDB(article: Article)
}