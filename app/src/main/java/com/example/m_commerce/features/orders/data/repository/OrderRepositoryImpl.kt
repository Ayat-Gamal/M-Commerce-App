package com.example.m_commerce.features.orders.data.repository

import com.example.m_commerce.features.orders.data.model.variables.DraftOrderCreateVariables
import com.example.m_commerce.features.orders.data.model.GraphQLRequest
import com.example.m_commerce.features.orders.data.model.variables.CompleteOrderVariables
import com.example.m_commerce.features.orders.data.remote.OrderDataSource
import com.example.m_commerce.features.orders.domain.entity.CompletedOrder
import com.example.m_commerce.features.orders.domain.entity.CreatedOrder
import com.example.m_commerce.features.orders.domain.entity.OrderHistory
import com.example.m_commerce.features.orders.domain.repository.OrderRepository
import com.example.m_commerce.features.orders.presentation.ui_state.OrderHistoryUiState
import com.example.m_commerce.features.orders.presentation.ui_state.OrderUiState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderDataSource: OrderDataSource
) : OrderRepository {
    override fun createOrder(
        body: GraphQLRequest<DraftOrderCreateVariables>,
        token: String
    ): Flow<CreatedOrder> = orderDataSource.createOrder(body, token)

    override fun completeOrder(
        body: GraphQLRequest<CompleteOrderVariables>,
        token: String
    ): Flow<CompletedOrder> = orderDataSource.completeOrder(body, token)

    override fun getOrders(): Flow<OrderHistoryUiState> {
        return orderDataSource.fetchOrders()
    }
}
