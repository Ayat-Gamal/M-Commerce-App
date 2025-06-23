package com.example.m_commerce.features.wishlist.presentation

import com.example.m_commerce.features.product.domain.entities.Product

sealed class WishlistUiState {
    data class Error(val error: String) : WishlistUiState()
    data object Empty : WishlistUiState()
    data object Loading : WishlistUiState()
    data object Search : WishlistUiState()
    data object Guest : WishlistUiState()
    data object NoNetwork : WishlistUiState()
    data class Success(val data: List<Product>) : WishlistUiState()
}