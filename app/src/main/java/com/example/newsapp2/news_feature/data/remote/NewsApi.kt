package com.example.newsapp2.news_feature.data.remote

import com.example.newsapp2.news_feature.data.remote.dto.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsApi {
@GET("top-headlines/category/{category}/us.json")
suspend fun getNewsByCategory(@Path("category") category: String): Response<NewsResponse>
}


