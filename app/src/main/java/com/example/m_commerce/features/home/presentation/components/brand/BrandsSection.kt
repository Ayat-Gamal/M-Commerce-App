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

    val filteredBrands = brands.drop(1).take(6)

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