package com.example.m_commerce.features.orders.data.remote

import com.example.m_commerce.features.orders.data.model.variables.DraftOrderCreateVariables
import com.example.m_commerce.features.orders.data.model.GraphQLRequest
import com.example.m_commerce.features.orders.data.model.variables.CompleteOrderVariables
import com.example.m_commerce.features.orders.domain.entity.CompletedOrder
import com.example.m_commerce.features.orders.domain.entity.CreatedOrder
import com.example.m_commerce.features.orders.domain.entity.OrderHistory
import com.example.m_commerce.features.orders.presentation.ui_state.OrderHistoryUiState
import kotlinx.coroutines.flow.Flow

interface OrderDataSource {
    fun createOrder(
        body: GraphQLRequest<DraftOrderCreateVariables>,
        token: String
    ): Flow<CreatedOrder>

    fun completeOrder(
        body: GraphQLRequest<CompleteOrderVariables>,
        token: String
    ): Flow<CompletedOrder>

    fun fetchOrders(): Flow<OrderHistoryUiState>

}