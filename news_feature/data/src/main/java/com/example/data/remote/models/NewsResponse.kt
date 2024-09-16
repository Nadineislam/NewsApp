package com.example.data.remote.models

import com.example.domain.models.NewsArticle

data class NewsResponse(
    val articles: List<NewsArticle>
)
