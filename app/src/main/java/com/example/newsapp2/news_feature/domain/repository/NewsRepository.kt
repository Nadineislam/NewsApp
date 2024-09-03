package com.example.newsapp2.news_feature.domain.repository

import com.example.newsapp2.news_feature.data.remote.dto.NewsResponse
import retrofit2.Response

interface NewsRepository {
    suspend fun getNewsByCategory(category: String): Response<NewsResponse>
}