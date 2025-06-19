package com.example.m_commerce.features.product.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.m_commerce.features.brand.domain.entity.ProductCardModel


@Composable
fun ProductsGridView(modifier: Modifier, products: List<ProductCardModel>, navigateToProduct: (ProductCardModel) -> Unit ) {
    LazyVerticalGrid(
        modifier  = modifier,
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 12.dp)
    ) {
        items(products.size) {
            ProductCard(
                product = products[it],
                onClick = {
                    navigateToProduct(products[it])
                },
                addToWishList = {}
            )
        }
    }
}