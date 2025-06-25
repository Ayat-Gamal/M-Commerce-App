package com.example.m_commerce.features.wishlist.presentation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.m_commerce.config.routes.AppRoutes
import com.example.m_commerce.core.shared.components.Empty
import com.example.m_commerce.core.shared.components.GuestMode
import com.example.m_commerce.core.shared.components.NoNetwork
import com.example.m_commerce.core.shared.components.SearchBarWithClear
import com.example.m_commerce.core.shared.components.default_top_bar.BackButton
import com.example.m_commerce.features.product.domain.entities.Product
import com.example.m_commerce.features.product.presentation.components.ProductsGridView
import com.example.m_commerce.features.search.presentation.SearchScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun WishListScreen(
    navController: NavHostController,
    viewModel: WishlistViewModel = hiltViewModel()
) {
    val query = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            if (FirebaseAuth.getInstance().currentUser != null) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    BackButton(navController)
                    SearchBarWithClear(
                        onQueryChange = {},
                        onClear = {},
                        enabled = false,
                        placeholder = "Search in wishlist...",
                        onclick = {
                            navController.navigate(AppRoutes.SearchScreen(true))
                        },
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }
            } else {
                BackButton(navController)
            }
        }
    ) { padding ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

//        Column(Modifier.padding(padding)) {
//            DefaultTopBar(title = "WishList", navController = navController)

        when (uiState) {
            is WishlistUiState.Success -> {
                Column(Modifier.padding(padding)) {
                    LoadData((uiState as WishlistUiState.Success).data, navController)
                }
            }

            WishlistUiState.Empty -> {
                Log.d("TAG", "WishlistUiState.Empty")
                Empty("No products added yet")
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
                Log.d("TAG", "WishlistUiState.Loading")
            }

            WishlistUiState.NoNetwork -> {
                Log.d("TAG", "WishlistUiState.NoNetwork")
                NoNetwork()
            }

            WishlistUiState.Search -> {
                Log.d("TAG", "WishListScreen: WishlistUiState.Search")
                SearchScreen(
                    navController,
                    isWishlist = false
//                    query, padding, navigateToProductDetails = { productId ->
//                    navController.navigate(AppRoutes.ProductDetailsScreen(productId))
//                }, { product ->
//                    viewModel.deleteProductFromWishlist(product.id)
//                    viewModel.getProducts()
//                }
                )
            }

            WishlistUiState.Guest -> {
                GuestMode(navController, "Wishlist", Icons.Default.FavoriteBorder)
            }
        }
    }
//    }


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
            viewModel.deleteProductFromWishlist(it.id)
            viewModel.getProducts()
        }) { product ->
        navController.navigate(AppRoutes.ProductDetailsScreen(product.id))
    }
}