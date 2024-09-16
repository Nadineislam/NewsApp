package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.NewsArticle
import com.example.domain.usecase.NewsUseCase
import com.example.presentation.intents.NewsIntent
import com.example.presentation.viewstates.NewsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<NewsViewState>(NewsViewState.Loading)
    val state = _state

    private val _selectedCategory = MutableStateFlow("business")
    val selectedCategory = _selectedCategory

    init {
        processIntent(NewsIntent.LoadNews(_selectedCategory.value))
    }

    fun processIntent(intent: NewsIntent) {
        when (intent) {
            is NewsIntent.LoadNews -> loadNews(intent.category)
        }
    }

    private fun loadNews(category: String) {
        _selectedCategory.value = category
        viewModelScope.launch {
            val response = newsUseCase(category)
            if (response.isSuccessful) {
                response.body()?.let {
                    _state.value = NewsViewState.Success(it.articles)
                } ?: run {
                    _state.value = NewsViewState.Error("No articles found")
                }
            } else {
                _state.value = NewsViewState.Error("Failed to load news")
            }
        }
    }

    fun findArticleByUrl(url: String): NewsArticle? {
        return (state.value as? NewsViewState.Success)?.articles?.find { it.url == url }
    }
}
