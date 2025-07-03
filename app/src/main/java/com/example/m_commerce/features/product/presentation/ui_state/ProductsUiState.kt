package com.example.m_commerce.features.product.presentation.ui_state

import com.example.m_commerce.features.product.domain.entities.Product

sealed class ProductsUiState {
    object Loading : ProductsUiState()
    object NoNetwork : ProductsUiState()
    data class Success(val products: List<Product>) : ProductsUiState()
    data class Error(val message: String) : ProductsUiState()
}