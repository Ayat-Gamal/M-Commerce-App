package com.example.m_commerce.features.home.presentation.components.category


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.m_commerce.features.categories.domain.entity.Category
import com.example.m_commerce.features.home.presentation.components.SectionTemplate

@Composable
fun CategorySection(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    navigateToCategories: () -> Unit,
    navigateToCategory: (Category) -> Unit
) {
    SectionTemplate(title = "Categories", seeAllOnClick = navigateToCategories) {
        LazyRow(
            modifier = modifier,
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories.take(6).size) { index ->
                CategoryMiniCard(
                    category = categories[index],
                    onClick = {
                        navigateToCategory(categories[index])
                    }
                )
            }
        }
    }
}