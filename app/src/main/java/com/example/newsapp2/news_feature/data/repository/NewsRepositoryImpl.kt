package com.example.newsapp2.news_feature.data.repository

import com.example.newsapp2.core.utils.Resource
import com.example.newsapp2.news_feature.data.remote.NewsApi
import com.example.newsapp2.news_feature.data.remote.dto.NewsResponse
import com.example.newsapp2.news_feature.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val apiService: NewsApi) :
    NewsRepository {

    override fun getNewsCategories(): Flow<Resource<NewsResponse>> = flow {
        emit(Resource.Loading())
        val response = apiService.getNewsCategories()
        if (response.isSuccessful) {
            emit(Resource.Success(response.body()?: NewsResponse("ok", 0, emptyList())))
        } else {
            emit(Resource.Error("Failed to fetch articles"))
        }
    }
}