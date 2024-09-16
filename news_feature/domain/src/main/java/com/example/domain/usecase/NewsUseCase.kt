package com.example.domain.usecase

import com.example.domain.repository.NewsRepository
import javax.inject.Inject

class NewsUseCase @Inject constructor(private val repository: NewsRepository) {
    suspend operator fun invoke(category: String) = repository.getNewsByCategory(category)

}