package com.example.newsapp2.news_feature.presentation.intents

import com.example.newsapp2.news_feature.data.remote.dto.NewsArticle

sealed class NewsIntent{
    data class LoadNews(val category: String) : NewsIntent()
    data class SelectArticle(val article: NewsArticle) : NewsIntent()


}
