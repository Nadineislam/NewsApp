package com.example.newsapp2.news_feature.domain.repository

import com.example.newsapp2.core.utils.Resource
import com.example.newsapp2.news_feature.data.remote.dto.NewsResponse
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNewsCategories(): Flow<Resource<NewsResponse>>
}