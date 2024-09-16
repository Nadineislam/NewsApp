package com.example.newsapp2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController

import com.example.newsapp2.ui.theme.NewsApp2Theme
import com.example.presentation.components.NewsNavGraph
import com.example.presentation.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: NewsViewModel by viewModels()
        setContent {
            NewsApp2Theme {
                val navController = rememberNavController()
                NewsNavGraph(navController = navController, viewModel = viewModel)
            }
        }
    }
}



