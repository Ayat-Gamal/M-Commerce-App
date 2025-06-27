package com.example.m_commerce.features.categories.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.m_commerce.core.shared.components.NoNetwork
import com.example.m_commerce.core.shared.components.default_top_bar.DefaultTopBar
import com.example.m_commerce.core.shared.components.screen_cases.FailedScreenCase
import com.example.m_commerce.core.shared.components.screen_cases.LoadingScreenCase
import com.example.m_commerce.features.brand.domain.entity.Brand
import com.example.m_commerce.features.brand.presentation.ui_state.BrandsUiState
import com.example.m_commerce.features.brand.presentation.viewmodel.BrandsViewModel
import com.example.m_commerce.features.categories.domain.entity.Category
import com.example.m_commerce.features.categories.presentation.components.CategoryCard
import com.example.m_commerce.features.categories.presentation.components.SubCategoryCard

@Composable
fun CategoryScreenUI(
    modifier: Modifier = Modifier,
    viewModel: BrandsViewModel = hiltViewModel(),
    navigateToCategory: (Brand) -> Unit,
    navigateToSubCategory: (Category) -> Unit
) {

    val state by viewModel.brandsState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getCategoriesData()
    }

    when (state) {
        is BrandsUiState.Loading -> {
            LoadingScreenCase()
        }

        is BrandsUiState.Error -> {
            FailedScreenCase(msg = (state as BrandsUiState.Error).message)
        }

        is BrandsUiState.Success -> {
            val categories = (state as BrandsUiState.Success).brands.takeLast(4)
            val subCategories = (state as BrandsUiState.Success).categories
            val pair = Pair(categories, subCategories)
            LoadedCase(modifier, pair, navigateToCategory, navigateToSubCategory)
        }

        BrandsUiState.NoNetwork -> NoNetwork()
    }

}

@Composable
private fun LoadedCase(
    modifier: Modifier,
    pairData: Pair<List<Brand>, List<Category>?>,
    navigateToCategory: (Brand) -> Unit,
    navigateToSubCategory: (Category) -> Unit
) {

    val categories = pairData.first
    val subCategories = pairData.second

    Scaffold(modifier = modifier, topBar = {
        DefaultTopBar(title = "Categories", navController = null)

    }) { padding ->
        LazyVerticalGrid(
            modifier = Modifier.padding(padding),
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(categories.size) { index ->
                CategoryCard(
                    category = categories[index],
                    modifier = Modifier.height(200.dp),
                    onClick = { navigateToCategory(categories[index]) })
            }
            if (subCategories != null) {
                items(subCategories.size) { index ->
                    SubCategoryCard(
                        category = subCategories[index],
                        modifier = Modifier.height(200.dp),
                        onClick = { navigateToSubCategory(subCategories[index]) })
                }
            }
        }

    }
}

