package com.example.m_commerce.features.home.presentation.components.brand

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.m_commerce.features.brand.presentation.components.BrandCard
import com.example.m_commerce.features.brand.domain.entity.Brand
import com.example.m_commerce.features.home.presentation.components.SectionTemplate

@Composable
fun BrandsSection(
    modifier: Modifier = Modifier,
    brands: List<Brand>,
    navigateToBrands: () -> Unit,
    navigateToBrand: (Brand) -> Unit
) {
    SectionTemplate(title = "Brands", seeAllOnClick = navigateToBrands) {
        Column(modifier = modifier) {

            filteredBrands.chunked(2).forEach { brandRow ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    brandRow.forEach { brand ->
                        BrandCard(
                            brand = brand,
                            modifier = Modifier.weight(1f),
                            onClick = {navigateToBrand(brand)}
                        )
                    }

                    if (brandRow.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}


val img = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/1015f/MainBefore.jpg"
val brands = listOf<Brand>(
    Brand("1", img, "Brand 1"),
    Brand("1", img, "Brand 2"),
    Brand("1", img, "Brand 3"),
    Brand("1", img, "Brand 4"),
    Brand("1", img, "Brand 5"),
    Brand("1", img, "Brand 6"),
    Brand("1", img, "Brand 7"),
    Brand("1", img, "Brand 8"),
    Brand("1", img, "Brand 9"),
    Brand("1", img, "Brand 10"),
    Brand("1", img, "Brand 11"),
    Brand("1", img, "Brand 12"),
    Brand("1", img, "Brand 13")
)
val filteredBrands = brands.take(6)
