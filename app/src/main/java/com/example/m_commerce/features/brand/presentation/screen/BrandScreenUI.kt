package com.example.m_commerce.features.brand.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.m_commerce.config.routes.AppRoutes
import com.example.m_commerce.core.shared.components.default_top_bar.DefaultTopBar
import com.example.m_commerce.features.brand.domain.entity.ProductCardModel
import com.example.m_commerce.features.product.presentation.components.ProductsGridView

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BrandScreenUI(modifier: Modifier = Modifier, brandId: String, navController: NavHostController) {

    Scaffold(modifier = modifier,
        topBar = {
            DefaultTopBar(
                title = brandId,
                navController = navController,
            )
        }
    ) { padding ->
        ProductsGridView(modifier = Modifier.padding(padding), products = products, addToWishList = {}) { product ->
            navController.navigate(AppRoutes.ProductDetailsScreen(product.id))
        }
    }

}


val img = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/1015f/MainBefore.jpg"


val products = listOf(
    ProductCardModel("1", "Product 1", img, 10.00, 9.2),
    ProductCardModel("2", "Product 2", img, 10.00),
    ProductCardModel("3", "Product 3", img, 10.00, 9.2),
    ProductCardModel("4", "Product 4", img, 10.00, 9.2),
    ProductCardModel("5", "Product 5", img, 10.00),
    ProductCardModel("6", "Product 6", img, 10.00),
    ProductCardModel("7", "Product 7", img, 10.00, 9.2),
    ProductCardModel("8", "Product 8", img, 10.00),
    ProductCardModel("9", "Product 9", img, 10.00, 9.2)
)

