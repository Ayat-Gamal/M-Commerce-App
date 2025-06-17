package com.example.m_commerce.features.brand.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
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
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BrandScreenUI(modifier: Modifier = Modifier, brandId: String, navController : NavHostController) {

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
        LazyVerticalGrid(columns = GridCells.Fixed(3)) {
            items(products.size) {
                ProductCard(
                    product = products[it],
                )
            }
        }
    }

}

@Composable
fun ProductCard(modifier: Modifier = Modifier) {
    Column (modifier = modifier) {


    }
}

val products = listOf(
    ProductCardModel(1,"Product 1",10.00, 9.2),
    ProductCardModel(1,"Product 2",10.00, 9.2),
    ProductCardModel(1,"Product 3",10.00, 9.2),
    ProductCardModel(1,"Product 4",10.00, 9.2),
    ProductCardModel(1,"Product 5",10.00, 9.2),
    ProductCardModel(1,"Product 6",10.00, 9.2),
    ProductCardModel(1,"Product 7",10.00, 9.2),
    ProductCardModel(1,"Product 8",10.00, 9.2),
    ProductCardModel(1,"Product 9",10.00, 9.2)
)

data class ProductCardModel(
    val id: Int,
    val name: String,
    val price: Double,
    val offer: Double
)