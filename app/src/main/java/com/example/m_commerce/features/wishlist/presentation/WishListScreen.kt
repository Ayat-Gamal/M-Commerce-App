package com.example.m_commerce.features.wishlist.presentation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.m_commerce.config.routes.AppRoutes
import com.example.m_commerce.core.shared.components.default_top_bar.BackButton
import com.example.m_commerce.features.home.presentation.components.SearchBarWithClear
import com.example.m_commerce.features.product.domain.entities.Product
import com.example.m_commerce.features.product.presentation.components.ProductsGridView
import com.example.m_commerce.features.search.presentation.SearchScreen

@Composable
fun WishListScreen(
    navController: NavHostController,
    paddingValues: PaddingValues,
    viewModel: WishlistViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getProducts()
    }

    var query by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                BackButton(navController)
                SearchBarWithClear(
                    query = query,
                    onQueryChange = {
                        query = it
                        viewModel.search(it)
                    },
                    onClear = {
                        query = ""
                        viewModel.search("")
                    }
                )
            }
        }
    ) { padding ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        Column(Modifier.padding(padding)) {
//            DefaultTopBar(title = "WishList", navController = navController)

            when (uiState) {
                is WishlistUiState.Success -> {
                    LoadData((uiState as WishlistUiState.Success).data, navController)
                }

                WishlistUiState.Empty -> {
                    Log.i("TAG", "WishlistUiState.Empty")
                }

                is WishlistUiState.Error -> {
                    Log.i(
                        "TAG",
                        "WishlistUiState.Error/ ${(uiState as WishlistUiState.Error).error}"
                    )
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

                WishlistUiState.Search -> SearchScreen()
            }
        }
    }


}

@Composable
fun LoadData(
    data: List<Product>,
    navController: NavHostController,
    viewModel: WishlistViewModel = hiltViewModel()
) {
    ProductsGridView(
        modifier = Modifier,
        products = data/*products*/,
        deleteFromWishList = {
            Log.d("TAG", "LoadData: product id: ${it.id}")
            viewModel.deleteProductFromWishlist(it.id)
            viewModel.getProducts()
        }) { product ->
        navController.navigate(AppRoutes.ProductDetailsScreen(product.id))
    }
}