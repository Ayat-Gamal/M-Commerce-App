package com.example.m_commerce.features.home.presentation.components.category


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.m_commerce.features.home.domain.entity.Category
import com.example.m_commerce.features.home.presentation.components.SectionTemplate

@Composable
fun CategorySection(modifier: Modifier = Modifier, navigateToCategory: () -> Unit) {

    SectionTemplate(title = "Categories", seeAllOnClick = navigateToCategory) {
        LazyRow(
            modifier = modifier,
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(categories.size) { index ->
                CategoryCard(
                    modifier = Modifier.width(120.dp),
                    category = categories[index],
                )
            }

        }
    }
}

val img = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/1015f/MainBefore.jpg"

val categories = listOf(
    Category(1, img, "Category 1"),
    Category(1, img, "Category 2"),
    Category(1, img, "Category 3"),
    Category(1, img, "Category 4"),
    Category(1, img, "Category 5"),
    Category(1, img, "Category 6"),
    Category(1, img, "Category 7"),
    Category(1, img, "Category 8"),
)