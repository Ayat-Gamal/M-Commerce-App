package com.example.m_commerce.features.brand.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.m_commerce.config.routes.AppRoutes
import com.example.m_commerce.features.brand.domain.entity.ProductCardModel
import com.example.m_commerce.features.product.presentation.components.ProductCard

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BrandScreenUI(modifier: Modifier = Modifier, brandId: String, navController: NavHostController) {

    Scaffold(modifier = modifier, topBar = {
        TopAppBar(

            title = {
                Text(text = brandId)
            },
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Localized description"
                    )
                }
            }

        )
    }) { padding ->
        LazyVerticalGrid(
            modifier = Modifier.padding(padding),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 12.dp)
        ) {
            items(products.size) {
                ProductCard(
                    product = products[it],
                    navigateToProductDetails = {
                        navController.navigate(AppRoutes.ProductDetailsScreen(products[it].id))
                    }
                )
            }
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

