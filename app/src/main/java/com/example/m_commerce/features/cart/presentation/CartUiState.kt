package com.example.m_commerce.features.cart.presentation

import com.example.m_commerce.features.cart.domain.entity.Cart

sealed class CartUiState {

    data class Error(val error: String) : CartUiState()
    data object Empty : CartUiState()
    data object Loading : CartUiState()
    data object Guest : CartUiState()
    data object NoNetwork : CartUiState()
    data class Success(val cart: Cart) : CartUiState()
}


