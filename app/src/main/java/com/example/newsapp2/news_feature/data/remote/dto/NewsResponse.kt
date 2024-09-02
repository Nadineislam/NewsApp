package com.example.newsapp2.news_feature.data.remote.dto

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)
