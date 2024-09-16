package com.example.presentation.viewstates

import com.example.domain.models.NewsArticle

sealed class NewsViewState{
    object Loading : NewsViewState()
    data class Success(val articles: List<NewsArticle>) : NewsViewState()
    data class Error(val message: String) : NewsViewState()
}
