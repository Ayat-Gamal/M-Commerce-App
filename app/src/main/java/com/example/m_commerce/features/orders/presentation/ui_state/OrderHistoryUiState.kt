package com.example.m_commerce.features.orders.presentation.ui_state

import com.example.m_commerce.features.orders.domain.entity.OrderHistory

sealed class OrderHistoryUiState {
    object Loading : OrderHistoryUiState()
    object NoNetwork : OrderHistoryUiState()
    data class Success(val orders: List<OrderHistory>) : OrderHistoryUiState()
    data class Error(val message: String) : OrderHistoryUiState()
}