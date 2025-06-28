package com.example.m_commerce.features.product.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.m_commerce.features.product.domain.entities.Product


@Composable
fun ProductsGridView(
    modifier: Modifier,
    products: List<Product>,
    deleteFromWishList: ((Product) -> Unit)? = null,
    navigateToProduct: (Product) -> Unit
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier,
        columns = StaggeredGridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalItemSpacing =  16.dp,
//        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
    ) {
        items(products.size) {
            ProductCard(
                product = products[it],
                onClick = {
                    navigateToProduct(products[it])
                },
                deleteFromWishList = if (deleteFromWishList != null) {
                    {
                        deleteFromWishList(products[it])
                        Log.d("TAG", "ProductsGridView: deleteFromWishList pressed ")
                    }
                } else null,

                )
        }
    }
//    LazyVerticalGrid(
//        modifier = modifier,
//        columns = GridCells.Fixed(2),
//        horizontalArrangement = Arrangement.spacedBy(16.dp),
//        verticalArrangement = Arrangement.spacedBy(16.dp),
//        contentPadding = PaddingValues(16.dp)
//    ) {
//        items(products.size) {
//            ProductCard(
//                product = products[it],
//                onClick = {
//                    navigateToProduct(products[it])
//                },
//                deleteFromWishList = if (deleteFromWishList != null) {
//                    {
//                        deleteFromWishList(products[it])
//                        Log.d("TAG", "ProductsGridView: deleteFromWishList pressed ")
//                    }
//                } else null,
//
//            )
//        }
//    }
}