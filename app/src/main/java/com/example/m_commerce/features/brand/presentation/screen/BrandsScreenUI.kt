package com.example.m_commerce.features.brand.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.m_commerce.config.routes.AppRoutes
import com.example.m_commerce.core.shared.components.default_top_bar.DefaultTopBar
import com.example.m_commerce.features.brand.domain.entity.Brand
import com.example.m_commerce.features.brand.presentation.components.BrandCard


@Composable
fun BrandsScreenUI(modifier: Modifier = Modifier, navController: NavHostController) {


    val brands = listOf<Brand>(
    Brand("1", img, "Brand 1"),
    Brand("1", img, "Brand 2"),
    Brand("1", img, "Brand 3"),
    Brand("1", img, "Brand 4"),
    Brand("1", img, "Brand 5"),
    Brand("1", img, "Brand 6"),
    Brand("1", img, "Brand 7"),
    Brand("1", img, "Brand 8"),
    Brand("1", img, "Brand 9"),
    Brand("1", img, "Brand 10"),
    Brand("1", img, "Brand 11"),
    Brand("1", img, "Brand 12"),
    Brand("1", img, "Brand 13")
)


    Scaffold(modifier = modifier, topBar = {
        DefaultTopBar(title = "Brands", navController = navController)

    }) { padding ->
        LazyVerticalGrid(
            modifier = Modifier.padding(padding),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(brands.size) { index ->
                BrandCard(brand = brands[index]) {
                    navController.navigate(AppRoutes.BrandDetailsScreen(brands[index].id !!))
                }
            }

        }
    }
}