package com.example.newsapp2.news_feature.data.remote

import com.example.newsapp2.news_feature.data.remote.dto.NewsResponse
import retrofit2.Response
import retrofit2.http.GET

interface NewsApi {
    @GET("top-headlines/category/health/in.json")
    suspend fun getNewsCategories(): Response<NewsResponse>
}