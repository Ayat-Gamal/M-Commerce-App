package com.example.m_commerce.features.orders.presentation.ui_state

import com.example.m_commerce.features.orders.domain.entity.CreatedOrder

sealed class OrderUiState {
    object Idle : OrderUiState()
    object Loading : OrderUiState()
    data class Success(val order: CreatedOrder) : OrderUiState()
    data class Error(val message: String) : OrderUiState()
}