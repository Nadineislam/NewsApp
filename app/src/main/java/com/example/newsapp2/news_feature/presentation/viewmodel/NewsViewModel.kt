package com.example.newsapp2.news_feature.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp2.core.utils.Resource
import com.example.newsapp2.core.utils.categorizeArticles
import com.example.newsapp2.news_feature.data.remote.dto.Article
import com.example.newsapp2.news_feature.domain.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<Resource<Map<String, List<Article>>>>(Resource.Loading())
    val state = _state

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            newsUseCase().collect { resource ->
                    _state.value = when (resource) {
                        is Resource.Success -> {
                            val articles = resource.data?.articles ?: emptyList()
                            Resource.Success(categorizeArticles(articles))
                        }
                        is Resource.Error -> Resource.Error(resource.message ?: "Unknown error")
                        is Resource.Loading -> Resource.Loading()
                    }
                }
        }
    }
}

