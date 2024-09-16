package com.example.presentation.intents

sealed class NewsIntent {
    data class LoadNews(val category: String) : NewsIntent()

}