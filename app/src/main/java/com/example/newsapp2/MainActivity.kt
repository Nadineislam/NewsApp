package com.example.newsapp2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp2.news_feature.presentation.viewmodel.NewsViewModel
import com.example.newsapp2.news_feature.presentation.viewstates.NewsViewState
import com.example.newsapp2.ui.theme.NewsApp2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: NewsViewModel by viewModels()

        setContent {
            NewsApp2Theme {
                    var selectedCategory by remember { mutableStateOf<String?>(null) }

                    if (selectedCategory == null) {
                        CategoryList(viewModel,onCategoryClick = { category ->
                            selectedCategory = category
                        })
                    } else {
                        CategoryList(viewModel,onCategoryClick = { category ->
                            selectedCategory = category
                        })
                    }

            }
        }
    }
}
@Composable
fun CategoryCard(category: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(7.dp)
            .size(80.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(50),
        elevation =  CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(50))
        ) {
            Text(
                text = category,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                ),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}


@Composable
fun CategoryList(viewModel: NewsViewModel, onCategoryClick: (String) -> Unit) {
    val state by viewModel.state.collectAsState()

    when (state) {
        is NewsViewState.Loading -> {
           // CircularProgressIndicator()
        }
        is NewsViewState.Error -> {
            Text(text = (state as NewsViewState.Error).message, color = Color.Red)
        }
        is NewsViewState.SuccessCategories -> {
            val categories = (state as NewsViewState.SuccessCategories).categories
            categories?.let {
                LazyRow {
                    items(it.keys.toList()) { category ->
                        CategoryCard(category = category) {
                            onCategoryClick(category)
                        }
                    }
                }
            }
        }
    }
}

