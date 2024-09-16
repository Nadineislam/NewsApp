package com.example.data.remote.models

data class NewsArticle(
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val author: String?,
    val content: String?
)
