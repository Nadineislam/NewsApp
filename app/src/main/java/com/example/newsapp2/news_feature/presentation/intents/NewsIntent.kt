package com.example.newsapp2.news_feature.presentation.intents

sealed class NewsIntent{
    object LoadCategories : NewsIntent()
}
