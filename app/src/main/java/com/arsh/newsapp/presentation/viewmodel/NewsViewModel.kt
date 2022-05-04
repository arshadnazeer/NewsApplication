package com.arsh.newsapp.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.arsh.newsapp.data.model.Article
import com.arsh.newsapp.data.model.NewsAPIResponse
import com.arsh.newsapp.data.util.Resource
import com.arsh.newsapp.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewsViewModel(
    private val app: Application,
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase,
    private val saveNewsUseCase: SaveNewsUseCase,
    private val getSavedNewsUseCase: GetSavedNewsUseCase,
    private val deleteSavedNewsUseCase: DeleteSavedNewsUseCase
) : AndroidViewModel(app) {

    val newHeadLines: MutableLiveData<Resource<NewsAPIResponse>> = MutableLiveData()

    fun getNewsHeadLines(country: String, page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            newHeadLines.postValue(Resource.Loading())
            try {
                if (isNetworkAvailable(app)) {
                    val apiResult = getTopHeadlinesUseCase.execute(country, page)
                    newHeadLines.postValue(apiResult)
                }
                newHeadLines.postValue(Resource.Error("No Internet Connection"))
            } catch (e: Exception) {
                newHeadLines.postValue(Resource.Error(e.message.toString()))
            }
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    // search news

    val searchNewsHeadlines: MutableLiveData<Resource<NewsAPIResponse>> = MutableLiveData()

    fun searchNews(country: String, page: Int, searchQuery: String) {
        viewModelScope.launch(Dispatchers.IO) {
            searchNewsHeadlines.postValue(Resource.Loading())
            try {
                if (isNetworkAvailable(app)) {
                    val apiResponse = getSearchedNewsUseCase.execute(country, page, searchQuery)
                    searchNewsHeadlines.postValue(apiResponse)
                }
                searchNewsHeadlines.postValue(Resource.Error("No Internet Connection"))
            } catch (e: Exception) {
                searchNewsHeadlines.postValue(Resource.Error(e.message.toString()))
            }
        }
    }

    //save
    fun saveArticle(article: Article){
        viewModelScope.launch(Dispatchers.IO) {
            saveNewsUseCase.execute(article)
        }
    }

   // get saved
    fun getSavedArticle() = liveData {
        getSavedNewsUseCase.execute().collect{
            emit(it)
        }
   }

    //delete
    fun deleteNews(article: Article){
        viewModelScope.launch(Dispatchers.IO) {
            deleteSavedNewsUseCase.execute(article)
        }
    }

}