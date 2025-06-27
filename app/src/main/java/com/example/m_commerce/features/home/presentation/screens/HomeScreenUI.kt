package com.example.m_commerce.features.home.presentation.screens

import android.util.Log
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.m_commerce.core.shared.components.NoNetwork
import com.example.m_commerce.core.shared.components.screen_cases.FailedScreenCase
import com.example.m_commerce.core.shared.components.screen_cases.LoadingScreenCase
import com.example.m_commerce.features.brand.domain.entity.Brand
import com.example.m_commerce.features.coupon.domain.entity.Coupon
import com.example.m_commerce.features.home.presentation.components.SearchSection
import com.example.m_commerce.features.home.presentation.components.brand.BrandsSection
import com.example.m_commerce.features.home.presentation.components.category.CategorySection
import com.example.m_commerce.features.home.presentation.components.specialoffer.SpecialOffersSection
import com.example.m_commerce.features.home.presentation.ui_state.HomeUiState
import com.example.m_commerce.features.home.presentation.viewmodel.HomeViewModel
import com.example.m_commerce.features.search.presentation.SearchScreen
import com.google.firebase.auth.FirebaseAuth


@Composable
fun HomeScreenUI(
    modifier: Modifier = Modifier,
    navigateToCategories: () -> Unit,
    navigateToCategory: (Brand) -> Unit,
    navigateToBrands: () -> Unit,
    navigateToBrand: (Brand) -> Unit,
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
    snackBarHostState: SnackbarHostState,

) {
    val uid = FirebaseAuth.getInstance().currentUser?.uid ?: "0"
    Log.i("TAG", "HomeScreenUI: uid: $uid")

    val scrollState = rememberScrollState()
    val activity = LocalActivity.current
    var query = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getHomeData()
    }

    BackHandler { activity?.finish() }

    val state by viewModel.dataState.collectAsStateWithLifecycle()

    when (state) {
        is HomeUiState.Loading -> LoadingScreenCase()
        is HomeUiState.Error -> FailedScreenCase(msg = (state as HomeUiState.Error).message)
        is HomeUiState.Success -> {
            val (brands, couponCodes) = (state as HomeUiState.Success)
            val categories = brands.takeLast(4)
            if (brands.isNotEmpty()) {
                LoadedData(
                    scrollState,
                    navigateToCategories,
                    navigateToCategory,
                    navigateToBrands,
                    navigateToBrand,
                    brands,
                    categories,
                    couponCodes,
                    navController,
                    snackBarHostState
                )
            } else {
                FailedScreenCase(msg = "No Data Found")
            }
        }

        HomeUiState.Search -> SearchScreen(
            navController = navController,
            snackBarHostState,
            isWishlist = false
        )

        HomeUiState.NoNetwork -> NoNetwork()
    }

}

@Composable
private fun LoadedData(
    scrollState: ScrollState,
    navigateToCategories: () -> Unit,
    navigateToCategory: (Brand) -> Unit,
    navigateToBrands: () -> Unit,
    navigateToBrand: (Brand) -> Unit,
    brands: List<Brand>,
    categories: List<Brand>,
    couponCodes: List<Coupon>,
    navController: NavHostController,
    snackBarHostState: SnackbarHostState
) {
    Column(
        Modifier
            .verticalScroll(scrollState)
            .wrapContentHeight()
    ) {
        SearchSection(navController = navController)

        SpecialOffersSection(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            couponCodes =couponCodes,
            snackBarHostState = snackBarHostState
        )

        CategorySection(
            Modifier
                .fillMaxWidth()
                .height(120.dp), categories, navigateToCategories, navigateToCategory
        )

        BrandsSection(Modifier.fillMaxWidth(), brands, navigateToBrands, navigateToBrand)

        Spacer(Modifier.height(112.dp))
    }
}





