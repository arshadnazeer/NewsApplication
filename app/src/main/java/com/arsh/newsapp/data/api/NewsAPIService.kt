package com.arsh.newsapp.data.api

import com.arsh.newsapp.BuildConfig
import com.arsh.newsapp.data.model.NewsAPIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPIService {
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country")
        country: String,
        @Query("page")
        page: Int,
        @Query("apiKey")
        api_Key : String = BuildConfig.API_KEY
    ) : Response<NewsAPIResponse>

    @GET("v2/top-headlines")
    suspend fun getSearchedArticles(
        @Query("country")
        country: String,
        @Query("page")
        page: Int,
        @Query("q")
        searchQuery : String,
        @Query("apiKey")
        api_Key: String = BuildConfig.API_KEY
    ) : Response<NewsAPIResponse>
}