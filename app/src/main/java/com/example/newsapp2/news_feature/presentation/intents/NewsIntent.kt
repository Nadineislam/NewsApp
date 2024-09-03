package com.example.newsapp2.news_feature.presentation.intents


sealed class NewsIntent {
    data class LoadNews(val category: String) : NewsIntent()

}
