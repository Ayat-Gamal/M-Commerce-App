package com.example.m_commerce.features.orders.domain.usecases

import android.util.Log
import com.example.m_commerce.features.orders.domain.entity.OrderHistory
import com.example.m_commerce.features.orders.domain.repository.OrderRepository
import com.example.m_commerce.features.orders.presentation.ui_state.OrderHistoryUiState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    operator fun invoke(): Flow<OrderHistoryUiState> {
        return repository.getOrders()
    }
}