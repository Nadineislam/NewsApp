package com.example.newsapp2.news_feature.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp2.core.utils.Resource
import com.example.newsapp2.core.utils.categorizeArticles
import com.example.newsapp2.news_feature.domain.usecase.NewsUseCase
import com.example.newsapp2.news_feature.presentation.intents.NewsIntent
import com.example.newsapp2.news_feature.presentation.viewstates.NewsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<NewsViewState>(NewsViewState.Loading)
    val state = _state.asStateFlow()

    init {
        processIntent(NewsIntent.LoadCategories)
    }

    private fun processIntent(intent: NewsIntent) {
        viewModelScope.launch {
            when (intent) {
                is NewsIntent.LoadCategories -> loadCategories()
            }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            newsUseCase().collect { resource ->
                _state.value = when (resource) {
                    is Resource.Success -> {
                        val articles = resource.data?.articles ?: emptyList()
                        NewsViewState.SuccessCategories(categorizeArticles(articles))
                    }
                    is Resource.Error -> NewsViewState.Error(resource.message ?: "Unknown error")
                    is Resource.Loading -> NewsViewState.Loading
                }
            }
        }
}}

