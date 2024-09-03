package com.example.newsapp2.news_feature.data.remote.dto

data class NewsArticle(
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val author: String?,
    val content: String?
)