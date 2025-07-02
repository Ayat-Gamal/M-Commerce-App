package com.example.m_commerce.features.brand.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.m_commerce.config.routes.AppRoutes
import com.example.m_commerce.core.shared.components.NoNetwork
import com.example.m_commerce.core.shared.components.default_top_bar.DefaultTopBar
import com.example.m_commerce.core.shared.components.screen_cases.FailedScreenCase
import com.example.m_commerce.core.shared.components.screen_cases.LoadingScreenCase
import com.example.m_commerce.core.utils.NetworkManager
import com.example.m_commerce.core.utils.extentions.capitalizeFirstLetters
import com.example.m_commerce.features.brand.presentation.viewmodel.BrandsViewModel
import com.example.m_commerce.features.product.domain.entities.Product
import com.example.m_commerce.features.product.presentation.components.ProductsGridView
import com.example.m_commerce.features.product.presentation.ui_state.ProductsUiState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BrandScreenUI(
    modifier: Modifier = Modifier,
    brandName: String,
    navController: NavHostController,
    viewModel: BrandsViewModel = hiltViewModel()
) {

    val state by viewModel.productsState.collectAsStateWithLifecycle()

    val ctx = LocalContext.current
    val networkManager = NetworkManager(ctx)
    val isOnline by networkManager.observeNetworkChanges()
        .collectAsStateWithLifecycle(networkManager.isNetworkAvailable())

    LaunchedEffect(isOnline) {
        viewModel.getProductsByBrandName(brandName)
    }

    Scaffold(modifier = modifier,
        topBar = {
            DefaultTopBar(
                title = brandName.capitalizeFirstLetters(),
                navController = navController,
            )
        }
    ) { padding ->
        when (state) {
            is ProductsUiState.Loading -> LoadingScreenCase()
            is ProductsUiState.Error -> FailedScreenCase(msg = (state as ProductsUiState.Error).message)
            is ProductsUiState.Success -> LoadedData(
                padding = padding,
                navController = navController,
                products = (state as ProductsUiState.Success).products
            )

            ProductsUiState.NoNetwork -> NoNetwork()
        }
    }

}

@Composable
private fun LoadedData(padding: PaddingValues, navController: NavHostController, products: List<Product>) {
    ProductsGridView(modifier = Modifier.padding(padding), products = products) { product ->
        navController.navigate(AppRoutes.ProductDetailsScreen(product.id))
    }
}


val img = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/1015f/MainBefore.jpg"


//val products: List<Product> = listOf(
//    Product(
//        "1",
//        "Product 1",
//        "Dummy description",
//        listOf(img),
//        "10.00",
//        "Dummy Category",
//        "USD",
//        listOf("Red", "Blue"),
//        listOf("S", "M", "L"),
//        emptyList(),
//        ""
//    )
//)


