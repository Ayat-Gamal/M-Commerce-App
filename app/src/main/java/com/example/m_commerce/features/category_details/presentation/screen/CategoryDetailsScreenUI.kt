package com.example.m_commerce.features.category_details.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.m_commerce.core.shared.components.default_top_bar.DefaultTopBar


@Composable
fun CategoryDetailsScreenUI(modifier: Modifier = Modifier, navController: NavHostController, categoryId: String) {
    Scaffold(modifier = modifier, topBar = {
        DefaultTopBar(title = categoryId, navController = navController)

    }) { padding ->
        Column(modifier = Modifier.padding(padding)) {
        }
    }
}