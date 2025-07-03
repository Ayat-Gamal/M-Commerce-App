package com.example.m_commerce.features.brand.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.m_commerce.config.routes.AppRoutes
import com.example.m_commerce.core.shared.components.NoNetwork
import com.example.m_commerce.core.shared.components.default_top_bar.DefaultTopBar
import com.example.m_commerce.core.shared.components.screen_cases.FailedScreenCase
import com.example.m_commerce.core.shared.components.screen_cases.Loading
import com.example.m_commerce.core.utils.NetworkManager
import com.example.m_commerce.features.brand.domain.entity.Brand
import com.example.m_commerce.features.brand.presentation.components.BrandCard
import com.example.m_commerce.features.brand.presentation.ui_state.BrandsUiState
import com.example.m_commerce.features.brand.presentation.viewmodel.BrandsViewModel


@Composable
fun BrandsDetailsScreenUI(
    modifier: Modifier = Modifier,
    viewModel: BrandsViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val state by viewModel.brandsState.collectAsStateWithLifecycle()
    val ctx = LocalContext.current
    val networkManager = NetworkManager(ctx)
    val isOnline by networkManager.observeNetworkChanges()
        .collectAsStateWithLifecycle(networkManager.isNetworkAvailable())

    LaunchedEffect(isOnline) {
        viewModel.getBrandsData()
    }

    when (state) {
        is BrandsUiState.Loading -> Loading()
        is BrandsUiState.Error -> FailedScreenCase(msg = (state as BrandsUiState.Error).message)
        is BrandsUiState.Success -> LoadedData(
            brands = (state as BrandsUiState.Success).brands.drop(1), navController = navController,
        )

        BrandsUiState.NoNetwork -> NoNetwork()
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
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(brands.size) { index ->
                BrandCard(brand = brands[index]) {
                    navController.navigate(AppRoutes.BrandDetailsScreen(brands[index].name ?: "Empty"))
                }
            }

        }
    }
}
