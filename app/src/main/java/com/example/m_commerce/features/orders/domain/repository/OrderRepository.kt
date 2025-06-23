package com.example.m_commerce.features.orders.domain.repository

import com.example.m_commerce.features.orders.data.model.variables.DraftOrderCreateVariables
import com.example.m_commerce.features.orders.data.model.GraphQLRequest
import com.example.m_commerce.features.orders.data.model.variables.CompleteOrderVariables
import com.example.m_commerce.features.orders.domain.entity.CompletedOrder
import com.example.m_commerce.features.orders.domain.entity.CreatedOrder
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun createOrder(
        body: GraphQLRequest<DraftOrderCreateVariables>,
        token: String
    ): Flow<CreatedOrder>

    fun completeOrder(
        body: GraphQLRequest<CompleteOrderVariables>,
        token: String
    ): Flow<CompletedOrder>
}