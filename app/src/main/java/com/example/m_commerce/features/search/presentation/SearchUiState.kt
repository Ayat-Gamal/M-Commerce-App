package com.example.m_commerce.features.search.presentation

import com.example.m_commerce.features.product.domain.entities.Product

sealed class SearchUiState {
    data class Success(val products: List<Product>) : SearchUiState()
    data object Loading : SearchUiState()
    data object Empty : SearchUiState()
    data class Error(val err: String) : SearchUiState()
}