package com.example.m_commerce.features.orders.data.remote

import com.example.m_commerce.features.orders.data.model.DraftOrderCreateVariables
import com.example.m_commerce.features.orders.data.model.GraphQLRequest
import com.example.m_commerce.features.orders.domain.entity.CreatedOrder
import kotlinx.coroutines.flow.Flow

interface OrderDataSource {
    fun createOrder(
        body: GraphQLRequest<DraftOrderCreateVariables>,
        token: String
    ): Flow<CreatedOrder>
}