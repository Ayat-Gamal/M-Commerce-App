package com.example.m_commerce.features.product.presentation

import com.example.m_commerce.features.product.domain.entities.Product

sealed class ProductUiState {

    data object Loading : ProductUiState()
    data object NoNetwork : ProductUiState()

    data class Success(
        val product: Product,
        val isFavorite: Boolean = false,
        val addToCartSuccess: Boolean = false
    ) : ProductUiState()

    data class Error(val message: String) : ProductUiState()
}


