package com.example.m_commerce.features.profile.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.m_commerce.config.theme.Background
import com.example.m_commerce.core.shared.components.default_top_bar.DefaultTopBar

@Composable
fun HelpCenterScreenUiLayout(navController: NavHostController) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Background)) {

        Scaffold(
            topBar = {
                DefaultTopBar(title = "Help Center", navController = navController)
            },
            containerColor = Color.Transparent // make Scaffold itself transparent
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
            }
        }
    }
}
