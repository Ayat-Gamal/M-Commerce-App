package com.example.m_commerce.features.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.m_commerce.features.home.domain.entity.Brand

@Composable
fun BrandsSection(
    modifier: Modifier = Modifier,
    navigateToBrands: () -> Unit
) {
    SectionTemplate(title = "Brands", seeAllOnClick = navigateToBrands) {
        Column(modifier = modifier) {

            brands.chunked(2).forEach { brandRow ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    brandRow.forEach { brand ->
                        BrandCard(
                            brand = brand,
                            modifier = Modifier.weight(1f)
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
@Composable
fun BrandCard(
    brand: Brand,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        AsyncImage(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth(),
            model = ImageRequest.Builder(LocalContext.current)
                .data(brand.image)
                .crossfade(true)
                .build(),
            contentDescription = "Brand image"
        )
        Text(text = brand.name)
    }
}


@Preview(showBackground = true)
@Composable
private fun PrevCard() {
    BrandCard(brand = brands[0])
}



val img = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/1015f/MainBefore.jpg"
val brands = listOf<Brand>(
    Brand(1, img, "Brand 1"),
    Brand(1, img, "Brand 2"),
    Brand(1, img, "Brand 3"),
    Brand(1, img, "Brand 4"),
    Brand(1, img, "Brand 5"),
    Brand(1, img, "Brand 6"),
    Brand(1, img, "Brand 7"),
    Brand(1, img, "Brand 8"),
    Brand(1, img, "Brand 9"),
    Brand(1, img, "Brand 10"),
    Brand(1, img, "Brand 11"),
    Brand(1, img, "Brand 12"),
    Brand(1, img, "Brand 13")
)