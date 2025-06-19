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
import com.example.m_commerce.features.brand.presentation.components.BrandCard
import com.example.m_commerce.features.home.presentation.components.brand.brands


@Composable
fun BrandsScreenUI(modifier: Modifier = Modifier, navController: NavHostController) {
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
                    navController.navigate(AppRoutes.BrandDetailsScreen(brands[index].id))
                }
            }

        }
    }
}