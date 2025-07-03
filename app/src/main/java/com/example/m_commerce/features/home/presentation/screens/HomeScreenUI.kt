package com.example.m_commerce.features.home.presentation.screens

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.m_commerce.core.shared.components.NoNetwork
import com.example.m_commerce.core.shared.components.screen_cases.FailedScreenCase
import com.example.m_commerce.core.shared.components.screen_cases.Loading
import com.example.m_commerce.core.utils.NetworkManager
import com.example.m_commerce.features.brand.domain.entity.Brand
import com.example.m_commerce.features.categories.domain.entity.Category
import com.example.m_commerce.features.coupon.domain.entity.Coupon
import com.example.m_commerce.features.home.presentation.components.SearchSection
import com.example.m_commerce.features.home.presentation.components.brand.BrandsSection
import com.example.m_commerce.features.home.presentation.components.category.CategorySection
import com.example.m_commerce.features.home.presentation.components.specialoffer.SpecialOffersSection
import com.example.m_commerce.features.home.presentation.ui_state.HomeUiState
import com.example.m_commerce.features.home.presentation.viewmodel.HomeViewModel


@Composable
fun HomeScreenUI(
    modifier: Modifier = Modifier,
    navigateToCategories: () -> Unit,
    navigateToCategory: (Category) -> Unit,
    navigateToBrands: () -> Unit,
    navigateToBrand: (Brand) -> Unit,
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
    snackBarHostState: SnackbarHostState,

    ) {

    val scrollState = rememberScrollState()
    val activity = LocalActivity.current

    val ctx = LocalContext.current
    val networkManager = NetworkManager(ctx)
    val isOnline by networkManager.observeNetworkChanges()
        .collectAsStateWithLifecycle(networkManager.isNetworkAvailable())

    LaunchedEffect(isOnline) {
        viewModel.getHomeData()
    }

    BackHandler { activity?.finish() }

    val state by viewModel.dataState.collectAsStateWithLifecycle()

    when (state) {
        is HomeUiState.Loading -> Loading()
        is HomeUiState.Error -> FailedScreenCase(msg = (state as HomeUiState.Error).message)
        is HomeUiState.Success -> {
            val (brands, subCategories, couponCodes, userName) = (state as HomeUiState.Success)
            val categories = brands.takeLast(4)
            if (brands.isNotEmpty()) {
                LoadedData(
                    scrollState,
                    navigateToCategories,
                    navigateToCategory,
                    navigateToBrands,
                    navigateToBrand,
                    brands,
                    subCategories,
                    couponCodes,
                    navController,
                    snackBarHostState,
                    userName
                )
            } else {
                FailedScreenCase(msg = "No Data Found")
            }
        }
        HomeUiState.NoNetwork -> NoNetwork()
    }

}

@Composable
private fun LoadedData(
    scrollState: ScrollState,
    navigateToCategories: () -> Unit,
    navigateToCategory: (Category) -> Unit,
    navigateToBrands: () -> Unit,
    navigateToBrand: (Brand) -> Unit,
    brands: List<Brand>,
    categories: List<Category>,
    couponCodes: List<Coupon>,
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    userName: String
) {
    Column(
        Modifier
            .verticalScroll(scrollState)
            .wrapContentHeight()
    ) {
        SearchSection(navController = navController, userName = userName)

        SpecialOffersSection(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            couponCodes = couponCodes,
            snackBarHostState = snackBarHostState
        )

        CategorySection(
            Modifier.fillMaxWidth(),
            categories,
            navigateToCategories,
            navigateToCategory
        )

        BrandsSection(Modifier.fillMaxWidth(), brands, navigateToBrands, navigateToBrand)

        Spacer(Modifier.height(80.dp))
    }
}





