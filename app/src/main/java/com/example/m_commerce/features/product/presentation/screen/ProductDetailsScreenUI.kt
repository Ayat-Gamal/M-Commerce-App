package com.example.m_commerce.features.product.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.m_commerce.core.shared.components.default_top_bar.DefaultTopBar


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductDetailsScreenUI(modifier: Modifier = Modifier, navController: NavHostController, productId: String) {

    Scaffold(modifier = modifier, topBar = {
        DefaultTopBar( title = productId, navController = navController)
    }) { padding ->
        Column(modifier = Modifier.padding(padding)) {
        }
    }
}