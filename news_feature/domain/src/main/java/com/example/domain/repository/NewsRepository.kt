package com.example.domain.repository

import com.example.domain.models.News
import retrofit2.Response
interface NewsRepository {
    suspend fun getNewsByCategory(category: String): Response<News>

}


