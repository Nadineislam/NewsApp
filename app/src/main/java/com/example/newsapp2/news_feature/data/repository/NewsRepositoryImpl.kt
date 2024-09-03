package com.example.newsapp2.news_feature.data.repository

import com.example.newsapp2.news_feature.data.remote.NewsApi
import com.example.newsapp2.news_feature.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val newsApi: NewsApi) :
    NewsRepository {
    override suspend fun getNewsByCategory(category: String) =
        newsApi.getNewsByCategory(category)

}