package com.arsh.newsapp.data.repository.datasourceImpl

import com.arsh.newsapp.data.db.ArticleDAO
import com.arsh.newsapp.data.model.Article
import com.arsh.newsapp.data.repository.datasource.NewsLocalDataSource
import kotlinx.coroutines.flow.Flow

class NewsLocalDataSourceImpl (
    private val articleDAO: ArticleDAO
        ): NewsLocalDataSource {
    override suspend fun saveNewsArticle(article: Article) {
        return articleDAO.insert(article)
    }

    override fun getSavedArticlesFromDB(): Flow<List<Article>> {
        return articleDAO.getAllArticles()
    }

    override suspend fun deleteSavedArticleFromDB(article: Article) {
        return articleDAO.deleteSavedArticle(article)
    }
}