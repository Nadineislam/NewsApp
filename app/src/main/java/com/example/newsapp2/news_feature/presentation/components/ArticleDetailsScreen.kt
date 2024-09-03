package com.example.newsapp2.news_feature.presentation.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.newsapp2.news_feature.data.remote.dto.NewsArticle

@Composable
fun ArticleDetailsScreen(article: NewsArticle?, navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        article?.let {
            Text(
                text = it.title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            AsyncImage(
                model = it.urlToImage,
                contentDescription = it.title,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Text(
                text = it.content ?: "No Content Available",
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
                navController.context.startActivity(intent)
            }) {
                Text("Read Full Article")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, it.url)
                }
                navController.context.startActivity(Intent.createChooser(shareIntent, "Share via"))
            }) {
                Text("Share Article")
            }
        } ?: run {
            BasicText("Article not found")
        }
    }

}

