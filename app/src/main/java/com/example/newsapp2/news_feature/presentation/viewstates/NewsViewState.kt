package com.example.newsapp2.news_feature.presentation.viewstates

import com.example.newsapp2.news_feature.data.remote.dto.Article

sealed class NewsViewState{
    object Loading : NewsViewState()
    data class SuccessCategories(val categories: Map<String, List<Article>>?) : NewsViewState()
    data class Error(val message: String) : NewsViewState()
}
