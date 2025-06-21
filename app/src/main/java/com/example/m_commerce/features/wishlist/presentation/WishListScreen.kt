package com.example.m_commerce.features.wishlist.presentation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.m_commerce.config.routes.AppRoutes
import com.example.m_commerce.core.shared.components.default_top_bar.DefaultTopBar
import com.example.m_commerce.features.product.domain.entities.Product
import com.example.m_commerce.features.product.presentation.components.ProductsGridView

@Composable
fun WishListScreen(
    navController: NavHostController,
    paddingValues: PaddingValues,
    viewModel: WishlistViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getProducts()
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(Modifier.padding(bottom = paddingValues.calculateBottomPadding())) {
        DefaultTopBar(title = "WishList", navController = navController)

        when (uiState) {
            is WishlistUiState.Success -> {
                LoadData((uiState as WishlistUiState.Success).data, navController)
            }

            WishlistUiState.Empty -> {
                Log.i("TAG", "WishlistUiState.Empty")
            }

            is WishlistUiState.Error -> {
                Log.i("TAG", "WishlistUiState.Error")
            }

            WishlistUiState.Loading -> {
                Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }
                Log.i("TAG", "WishlistUiState.Loading")
            }

            WishlistUiState.NoNetwork -> {
                Log.i("TAG", "WishlistUiState.NoNetwork")
            }
        }
    }
}

@Composable
fun LoadData(data: List<Product>, navController: NavHostController) {
    Log.i("TAG", "WishlistUiState.Success: ${data.size}")
    ProductsGridView(modifier = Modifier, products = data/*products*/) { product ->
        navController.navigate(AppRoutes.ProductDetailsScreen(product.id))
    }
}