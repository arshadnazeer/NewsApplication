package com.arsh.newsapp.domain.usecase

import com.arsh.newsapp.data.model.NewsAPIResponse
import com.arsh.newsapp.data.util.Resource
import com.arsh.newsapp.domain.repository.NewsRepository

class GetTopHeadlinesUseCase(
    private val newsRepository: NewsRepository
) {
    suspend fun execute(country : String, page : Int) : Resource<NewsAPIResponse>{
        return newsRepository.getTopNewsHeadlinesUseCase(country,page)
    }
}