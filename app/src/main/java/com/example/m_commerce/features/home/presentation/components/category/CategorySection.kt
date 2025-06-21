package com.example.m_commerce.features.home.presentation.components.category


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.m_commerce.features.categories.domain.entity.Category
import com.example.m_commerce.features.home.presentation.components.SectionTemplate

@Composable
fun CategorySection(modifier: Modifier = Modifier, categories: List<Category>, navigateToCategories: () -> Unit, navigateToCategory: (Category) -> Unit) {

    SectionTemplate(title = "Categories", seeAllOnClick = navigateToCategories) {
        LazyRow(
            modifier = modifier,
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(categories.take(6).size) { index ->
                CategoryMiniCard(
                    modifier = Modifier.width(95.dp),
                    category = categories[index],
                    onClick = {
                        navigateToCategory(categories[index])
                    }
                )
            }

        }
    }
}

val img = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/1015f/MainBefore.jpg"

val categories = listOf(
    Category("1", img, "Category 1"),
    Category("2", img, "Category 2"),
    Category("3", img, "Category 3"),
    Category("4", img, "Category 4"),
    Category("5", img, "Categorasdy 5"),
    Category("6", img, "Category 6"),
    Category("7", img, "Category 7"),
    Category("8", img, "Category 8"),
)
