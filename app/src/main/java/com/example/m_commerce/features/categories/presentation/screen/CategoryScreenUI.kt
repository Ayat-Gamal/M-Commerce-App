package com.example.m_commerce.features.categories.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.m_commerce.core.shared.components.default_top_bar.DefaultTopBar
import com.example.m_commerce.features.categories.presentation.components.CategoryCard
import com.example.m_commerce.features.categories.domain.entity.Category
import com.example.m_commerce.features.home.presentation.components.category.categories

@Composable
fun CategoryScreenUI(modifier: Modifier = Modifier, navigateToCategory: (Category) -> Unit) {
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
                CategoryCard(category = categories[index], modifier = Modifier.height(200.dp), onClick = {navigateToCategory(categories[index])})
            }
        }

    }
}

