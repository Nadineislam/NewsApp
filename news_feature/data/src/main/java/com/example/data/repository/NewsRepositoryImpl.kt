package com.example.data.repository

import com.example.data.datastore.NewsDataStore
import com.example.data.remote.NewsApi
import com.example.data.remote.models.NewsMapper
import com.example.domain.models.News
import com.example.domain.repository.NewsRepository
import kotlinx.coroutines.flow.firstOrNull
import retrofit2.Response
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val newsDataStore: NewsDataStore
) :
    NewsRepository {
    // In data module
// In data module
    override suspend fun getNewsByCategory(category: String): Response<News> {
        return try {
            val response = newsApi.getNewsByCategory(category)
            if (response.isSuccessful) {
                val newsResponse = response.body()
                val domainNews = newsResponse?.let { NewsMapper.mapToDomain(it) }
                if (domainNews != null) {
                    // Save the fetched news to DataStore
                    newsDataStore.saveLastNews(domainNews)
                    Response.success(domainNews)
                } else {
                    // Handle case where newsResponse is null
                    val cachedResponse = newsDataStore.lastNewsFlow.firstOrNull()
                    Response.success(cachedResponse)
                }
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

