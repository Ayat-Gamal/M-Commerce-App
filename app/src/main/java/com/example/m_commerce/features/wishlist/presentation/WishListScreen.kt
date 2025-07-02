package com.example.m_commerce.features.wishlist.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.m_commerce.R
import com.example.m_commerce.config.routes.AppRoutes
import com.example.m_commerce.core.shared.components.BlockingLoadingOverlay
import com.example.m_commerce.core.shared.components.Empty
import com.example.m_commerce.core.shared.components.Failed
import com.example.m_commerce.core.shared.components.NoNetwork
import com.example.m_commerce.core.shared.components.SvgButton
import com.example.m_commerce.core.shared.components.default_top_bar.DefaultTopBar
import com.example.m_commerce.core.shared.components.screen_cases.Loading
import com.example.m_commerce.core.utils.NetworkManager
import com.example.m_commerce.features.product.domain.entities.Product
import com.example.m_commerce.features.product.presentation.components.ProductsGridView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Composable
fun WishListScreen(
    snackBarHostState: SnackbarHostState,
    navController: NavHostController,
    viewModel: WishlistViewModel = hiltViewModel(),
) {

    val ctx = LocalContext.current
    val networkManager = NetworkManager(ctx)
    val isOnline by networkManager.observeNetworkChanges()
        .collectAsStateWithLifecycle(networkManager.isNetworkAvailable())
    val isLoading = viewModel.isLoading

    LaunchedEffect(isOnline) {
        viewModel.getProducts()
    }
    LaunchedEffect(Unit) {
        viewModel.message.collect { event ->
            snackBarHostState.currentSnackbarData?.dismiss()

            val result = snackBarHostState.showSnackbar(
                message = event.message,
                actionLabel = event.actionLabel,
                duration = SnackbarDuration.Short
            )

            if (result == SnackbarResult.ActionPerformed) {
                event.onAction?.invoke()
            }
        }
    }

    Scaffold(topBar = {
        if (FirebaseAuth.getInstance().currentUser != null) {
            DefaultTopBar(
                title = "Wishlist", navController = navController
            ) {
                SvgButton(R.raw.search) {
                    navController.navigate(AppRoutes.SearchScreen(true))
                }
            }
        }
    }) { padding ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        when (uiState) {
            is WishlistUiState.Success -> {
                Column(Modifier.padding(padding)) {
                    LoadData(
                        (uiState as WishlistUiState.Success).data,
                        navController,
                        isLoading = isLoading
                    )
                }
            }

            WishlistUiState.Empty -> {
                Empty("No products added yet")
            }

            is WishlistUiState.Error -> {
                Failed((uiState as WishlistUiState.Error).error)
            }

            WishlistUiState.Loading -> {
                Loading()
            }

            WishlistUiState.NoNetwork -> {
                NoNetwork()
            }
        }
    }
}

@Composable
private fun LoadData(
    data: List<Product>,
    navController: NavHostController,
    viewModel: WishlistViewModel = hiltViewModel(),
    isLoading: Boolean
) {
    Box(Modifier.fillMaxSize()) {

        ProductsGridView(modifier = Modifier, products = data, deleteFromWishList = {
            viewModel.deleteProductFromWishlist(it.id)
        }) { product ->
            navController.navigate(AppRoutes.ProductDetailsScreen(product.id))
        }
        BlockingLoadingOverlay(isLoading)
    }
}