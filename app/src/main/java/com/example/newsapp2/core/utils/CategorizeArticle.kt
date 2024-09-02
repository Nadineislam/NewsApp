package com.example.newsapp2.core.utils

import com.example.newsapp2.news_feature.data.remote.dto.Article

fun categorizeArticles(articles: List<Article>): Map<String, List<Article>> {
    return articles.groupBy { article ->
        when {
            article.content?.contains("Health", ignoreCase = true) == true ||
                    article.title?.contains("Health", ignoreCase = true) == true ||
                    article.description?.contains("Health", ignoreCase = true) == true -> "Health"

            article.content?.contains("covid", ignoreCase = true) == true ||
                    article.title?.contains("covid", ignoreCase = true) == true ||
                    article.description?.contains("covid", ignoreCase = true) == true -> "COVID-19"

            article.content?.contains("Science", ignoreCase = true) == true ||
                    article.title?.contains("Science", ignoreCase = true) == true ||
                    article.description?.contains("Science", ignoreCase = true) == true -> "Science"

            else -> "General"
        }
    }
}
