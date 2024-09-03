package com.example.newsapp2.news_feature.presentation.components

import android.net.Uri
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.navigation.NavController
import com.example.newsapp2.news_feature.data.remote.dto.NewsArticle
import com.example.newsapp2.news_feature.presentation.intents.NewsIntent
import com.example.newsapp2.news_feature.presentation.viewmodel.NewsViewModel
import com.example.newsapp2.news_feature.presentation.viewstates.NewsViewState
import java.util.Locale

@Composable
fun NewsScreen(navController: NavController, viewModel: NewsViewModel) {
    val state by viewModel.state.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    Column {
        val categories = listOf(
            "business",
            "entertainment",
            "general",
            "health",
            "science",
            "sports",
            "technology"
        )
        NewsCategoryChips(
            categories = categories,
            selectedCategory = selectedCategory,
            onCategorySelected = { category ->
                viewModel.processIntent(NewsIntent.LoadNews(category))
            }
        )
        when (state) {
            is NewsViewState.Loading -> {
                CircularProgressIndicator()
            }

            is NewsViewState.Success -> {
                NewsList(
                    newsArticles = (state as NewsViewState.Success).articles,
                    onArticleClick = { encodedUrl ->
                        navController.navigate("article_details/$encodedUrl")
                    }
                )
            }

            is NewsViewState.Error -> {
                BasicText((state as NewsViewState.Error).message)
            }
        }
    }
}

@Composable
fun NewsCategoryChips(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 8.dp)
    ) {
        categories.forEach { category ->
            val isSelected = category == selectedCategory
            AssistChip(
                onClick = { onCategorySelected(category) },
                label = {
                    Text(
                        category.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                        color = if (isSelected) Color.Black else Color.White
                    )
                },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray
                ),
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}

@Composable
fun NewsList(newsArticles: List<NewsArticle>, onArticleClick: (String) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(newsArticles) { article ->
            NewsItem(article = article, onClick = {
                val url = article.url
                val encodedUrl = Uri.encode(url)
                onArticleClick(encodedUrl)
            })
        }
    }
}

