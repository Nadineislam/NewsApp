package com.example.presentation.components

import android.net.Uri
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.domain.models.NewsArticle
import com.example.presentation.intents.NewsIntent
import com.example.presentation.viewmodel.NewsViewModel
import com.example.presentation.viewstates.NewsViewState
import java.util.Locale

@Composable
fun NewsScreen(navController: NavController, viewModel: NewsViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()

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
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
              //      CircularProgressIndicator()
                }
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
                Text((state as NewsViewState.Error).message)
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

