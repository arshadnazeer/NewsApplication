package com.arsh.newsapp.data.repository.datasource

import com.arsh.newsapp.data.api.NewsAPIService
import com.arsh.newsapp.data.model.NewsAPIResponse
import retrofit2.Response

interface NewsRemoteDataSource {
    suspend fun getTopHeadlines(country : String, page: Int) : Response<NewsAPIResponse>
    suspend fun getSearchedNews(country: String, page: Int, searchQuery : String) : Response<NewsAPIResponse>
}