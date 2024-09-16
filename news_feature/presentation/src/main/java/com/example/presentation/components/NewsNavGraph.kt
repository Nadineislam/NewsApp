package com.example.presentation.components

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.presentation.viewmodel.NewsViewModel

@Composable
fun NewsNavGraph(navController: NavHostController, viewModel: NewsViewModel) {
    NavHost(navController = navController, startDestination = "news_list") {
        composable("news_list") {
            NewsScreen(navController = navController, viewModel = viewModel)
        }
        composable(
            "article_details/{url}",
            arguments = listOf(navArgument("url") { type = NavType.StringType })
        ) { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url") ?: return@composable
            val decodedUrl = Uri.decode(url)
            val article = viewModel.findArticleByUrl(decodedUrl)
            ArticleDetailsScreen(article = article, navController = navController)
        }
    }
}