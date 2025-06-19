package com.example.m_commerce.features.wishlist.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.m_commerce.config.routes.AppRoutes
import com.example.m_commerce.core.shared.components.default_top_bar.DefaultTopBar
import com.example.m_commerce.features.brand.presentation.screen.products
import com.example.m_commerce.features.product.presentation.components.ProductsGridView

@Composable
fun WishListScreen(navController: NavHostController) {
    Column {
        DefaultTopBar(title = "WishList", navController = navController)

        ProductsGridView(modifier = Modifier, products = products) { product ->
            navController.navigate(AppRoutes.ProductDetailsScreen(product.id))
        }
    }
}