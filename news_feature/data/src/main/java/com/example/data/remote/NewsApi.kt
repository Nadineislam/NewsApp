package com.example.data.remote

import com.example.domain.models.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsApi {
    @GET("top-headlines/category/{category}/us.json")
    suspend fun getNewsByCategory(@Path("category") category: String): Response<News>
}