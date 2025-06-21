package com.example.m_commerce.features.brand.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.m_commerce.config.routes.AppRoutes
import com.example.m_commerce.core.shared.components.default_top_bar.DefaultTopBar
import com.example.m_commerce.features.brand.domain.entity.Brand
import com.example.m_commerce.features.brand.presentation.components.BrandCard
import com.example.m_commerce.features.brand.presentation.ui_state.BrandsUiState
import com.example.m_commerce.features.brand.presentation.viewmodel.BrandsViewModel


@Composable
fun BrandsScreenUI(
    modifier: Modifier = Modifier,
    viewModel: BrandsViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val state by viewModel.dataState.collectAsStateWithLifecycle()

    when (state) {
        is BrandsUiState.Loading -> Loading()
        is BrandsUiState.Error -> Failed(msg = (state as BrandsUiState.Error).message)
        is BrandsUiState.Success -> LoadedData(
            brands = (state as BrandsUiState.Success).brands.drop(1), navController = navController,
        )
    }
}


@Composable
private fun Loading(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun Failed(modifier: Modifier = Modifier, msg: String) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(msg)
    }
}

@Composable
private fun LoadedData(modifier: Modifier = Modifier, brands: List<Brand>, navController: NavHostController) {
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
                    navController.navigate(AppRoutes.BrandDetailsScreen(brands[index].id!!))
                }
            }

        }
    }
}
