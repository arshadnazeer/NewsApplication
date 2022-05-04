package com.arsh.newsapp.data.repository.datasourceImpl

import com.arsh.newsapp.data.api.NewsAPIService
import com.arsh.newsapp.data.model.NewsAPIResponse
import com.arsh.newsapp.data.repository.datasource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(
    private val newsAPIService: NewsAPIService,

) : NewsRemoteDataSource {
    override suspend fun getTopHeadlines(country: String, page: Int): Response<NewsAPIResponse> {
        return  newsAPIService.getTopHeadlines(country,page)
    }

    override suspend fun getSearchedNews(
        country: String,
        page: Int,
        searchQuery: String
    ): Response<NewsAPIResponse> {
        return newsAPIService.getSearchedArticles(country,page, searchQuery)
    }

}