package com.example.m_commerce.features.categories.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.m_commerce.config.routes.AppRoutes
import com.example.m_commerce.core.shared.components.default_top_bar.DefaultTopBar
import com.example.m_commerce.features.brand.presentation.screen.products
import com.example.m_commerce.features.product.presentation.components.ProductsGridView


@Composable
fun CategoryDetailsScreenUI(modifier: Modifier = Modifier, navController: NavHostController, categoryId: String) {
    Scaffold(modifier = modifier, topBar = {
        DefaultTopBar(title = categoryId, navController = navController)

    }) { padding ->
        ProductsGridView( modifier = Modifier.padding(padding), products = products) { product ->
            navController.navigate(AppRoutes.ProductDetailsScreen(product.id))
        }
    }
}