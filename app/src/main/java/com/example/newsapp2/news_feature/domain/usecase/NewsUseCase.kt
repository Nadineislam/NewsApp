package com.example.newsapp2.news_feature.domain.usecase

import com.example.newsapp2.news_feature.domain.repository.NewsRepository
import javax.inject.Inject

class NewsUseCase @Inject constructor(private val repository: NewsRepository) {

   suspend operator fun invoke(category: String) = repository.getNewsByCategory(category)

}