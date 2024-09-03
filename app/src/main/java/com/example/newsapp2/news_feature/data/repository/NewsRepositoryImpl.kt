package com.example.newsapp2.news_feature.data.repository

import com.example.newsapp2.news_feature.data.datastore.NewsDataStore
import com.example.newsapp2.news_feature.data.remote.NewsApi
import com.example.newsapp2.news_feature.data.remote.dto.NewsResponse
import com.example.newsapp2.news_feature.domain.repository.NewsRepository
import kotlinx.coroutines.flow.firstOrNull
import retrofit2.Response
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val newsApi: NewsApi, private val newsDataStore: NewsDataStore) :
    NewsRepository {
    override suspend fun getNewsByCategory(category: String): Response<NewsResponse> {
        return try {
            val response = newsApi.getNewsByCategory(category)
            if (response.isSuccessful) {
                response.body()?.let {
                    // Save the fetched news to DataStore
                    newsDataStore.saveLastNews(it)
                }
                response
            } else {
                val cachedResponse = newsDataStore.lastNewsFlow.firstOrNull()
                Response.success(cachedResponse)
            }
        } catch (e: Exception) {
            val cachedResponse = newsDataStore.lastNewsFlow.firstOrNull()
            Response.success(cachedResponse)
        }
    }

}