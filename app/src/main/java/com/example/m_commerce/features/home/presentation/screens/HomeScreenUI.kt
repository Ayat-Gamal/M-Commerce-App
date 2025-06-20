package com.example.m_commerce.features.home.presentation.screens

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.m_commerce.features.brand.domain.entity.Brand
import com.example.m_commerce.features.brand.ui_state.HomeUiState
import com.example.m_commerce.features.categories.domain.entity.Category
import com.example.m_commerce.features.home.presentation.components.SearchSection
import com.example.m_commerce.features.home.presentation.components.brand.BrandsSection
import com.example.m_commerce.features.home.presentation.components.category.CategorySection
import com.example.m_commerce.features.home.presentation.components.specialoffer.SpecialOffersSection
import com.example.m_commerce.features.home.presentation.viewmodel.HomeViewModel


@Composable
fun HomeScreenUI(
    modifier: Modifier = Modifier,
    navigateToCategories: () -> Unit,
    navigateToCategory: (Category) -> Unit,
    navigateToSpecialOffers: () -> Unit,
    navigateToBrands: () -> Unit,
    navigateToBrand: (Brand) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val scrollState = rememberScrollState()
    val activity = LocalActivity.current

    LaunchedEffect(Unit) {
        viewModel.getHomeData()
    }

    BackHandler { activity?.finish() }


    val state by viewModel.dataState.collectAsStateWithLifecycle()

    when (state) {
        is HomeUiState.Loading -> Loading()
        is HomeUiState.Error -> Failed(msg = (state as HomeUiState.Error).message)
        is HomeUiState.Success -> {
            val (brands, categories) = (state as HomeUiState.Success)
            if (brands.isNotEmpty() && categories.isNotEmpty()) {
                LoadedData(
                    scrollState,
                    navigateToSpecialOffers,
                    navigateToCategories,
                    navigateToCategory,
                    navigateToBrands,
                    navigateToBrand,
                    brands,
                    categories
                )
            } else {
                Failed(msg = "No Data Found")
            }
        }
    }

}

@Composable
private fun Loading(modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun Failed(modifier: Modifier = Modifier, msg: String) {
    Box(contentAlignment = Alignment.Center) {
        Text(msg)
    }
}

@Composable
private fun LoadedData(
    scrollState: ScrollState,
    navigateToSpecialOffers: () -> Unit,
    navigateToCategories: () -> Unit,
    navigateToCategory: (Category) -> Unit,
    navigateToBrands: () -> Unit,
    navigateToBrand: (Brand) -> Unit,
    brands: List<Brand>,
    categories: List<Category>
) {
    Column(
        Modifier
            .verticalScroll(scrollState)
            .wrapContentHeight()
    ) {
        SearchSection()

        SpecialOffersSection(
            Modifier
                .fillMaxWidth()
                .height(200.dp), navigateToSpecialOffers
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





