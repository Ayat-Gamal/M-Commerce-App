package com.example.m_commerce.features.categories.presentation.screen

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.m_commerce.config.routes.AppRoutes
import com.example.m_commerce.core.shared.components.NoNetwork
import com.example.m_commerce.core.shared.components.default_top_bar.DefaultTopBar
import com.example.m_commerce.core.shared.components.screen_cases.FailedScreenCase
import com.example.m_commerce.core.shared.components.screen_cases.LoadingScreenCase
import com.example.m_commerce.core.utils.extentions.capitalizeFirstLetters
import com.example.m_commerce.features.brand.presentation.viewmodel.BrandsViewModel
import com.example.m_commerce.features.product.domain.entities.Product
import com.example.m_commerce.features.product.presentation.components.ProductsGridView
import com.example.m_commerce.features.product.presentation.ui_state.ProductsUiState


@Composable
fun CategoryDetailsScreenUI(
    modifier: Modifier = Modifier, navController: NavHostController, categoryName: String, viewModel: BrandsViewModel = hiltViewModel()
) {
    val state by viewModel.productsState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getProductsByBrandName(categoryName)
    }

    when (state) {
        is ProductsUiState.Error -> {
            FailedScreenCase(msg = (state as ProductsUiState.Error).message)
            Log.d("CategoryDetails", "FAILED")
        }

        is ProductsUiState.Loading -> {
            Log.d("CategoryDetails", "LOADING")

            LoadingScreenCase()
        }

        is ProductsUiState.Success -> {
            val products = (state as ProductsUiState.Success).products
            Log.d("CategoryDetails", "CategoryDetailsScreenUI: ${categoryName}")
            LoadedCase(modifier, categoryName, navController, products)
        }

        ProductsUiState.NoNetwork -> NoNetwork()
    }
}

@Composable
private fun LoadedCase(
    modifier: Modifier,
    categoryId: String,
    navController: NavHostController,
    products: List<Product>
) {
    Scaffold(modifier = modifier, topBar = {
        DefaultTopBar(title = categoryId.capitalizeFirstLetters(), navController = navController)

    }) { padding ->
        ProductsGridView(modifier = Modifier.padding(padding), products = products) { product ->
            navController.navigate(AppRoutes.ProductDetailsScreen(product.id))
        }
    }
}