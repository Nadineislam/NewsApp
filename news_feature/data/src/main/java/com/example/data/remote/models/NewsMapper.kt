package com.example.data.remote.models

import com.example.domain.models.News
import com.example.domain.models.NewsArticle

object NewsMapper {

    fun mapToDomain(newsResponse: News): News {
        return News(
            articles = newsResponse.articles.map { mapArticleToDomain(it) }
        )
    }

    private fun mapArticleToDomain(dataArticle: NewsArticle): NewsArticle {
        return NewsArticle(
            title = dataArticle.title,
            description = dataArticle.description,
            url = dataArticle.url,
            urlToImage = dataArticle.urlToImage,
            publishedAt = dataArticle.publishedAt,
            author = dataArticle.author,
            content = dataArticle.content
        )
    }
}

