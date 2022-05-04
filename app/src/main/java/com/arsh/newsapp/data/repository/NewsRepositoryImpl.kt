package com.arsh.newsapp.data.repository

import com.arsh.newsapp.data.model.Article
import com.arsh.newsapp.data.model.NewsAPIResponse
import com.arsh.newsapp.data.repository.datasource.NewsLocalDataSource
import com.arsh.newsapp.data.repository.datasource.NewsRemoteDataSource
import com.arsh.newsapp.data.util.Resource
import com.arsh.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsLocalDataSource: NewsLocalDataSource
) : NewsRepository {
    override suspend fun getTopNewsHeadlinesUseCase(country: String, page: Int): Resource<NewsAPIResponse> {
        return responseToResource(newsRemoteDataSource.getTopHeadlines(country,page))
    }

    override suspend fun getSearchedNewsUseCase(country: String, page: Int, searchQuery: String): Resource<NewsAPIResponse> {
        return responseToResource(newsRemoteDataSource.getSearchedNews(country, page, searchQuery))
    }

    override suspend fun saveNewsUseCase(article: Article) {
        newsLocalDataSource.saveNewsArticle(article)
    }

    override fun getSavedNewsUseCase(): Flow<List<Article>> {
        return newsLocalDataSource.getSavedArticlesFromDB()
    }

    override suspend fun deleteSavedNewsUseCase(article: Article) {
        newsLocalDataSource.deleteSavedArticleFromDB(article)
    }

    private fun responseToResource(response: Response<NewsAPIResponse>) : Resource<NewsAPIResponse>{
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }
}