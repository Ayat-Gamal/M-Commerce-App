package com.example.m_commerce.features.orders.domain.repository

import com.example.m_commerce.features.orders.data.model.DraftOrderCreateVariables
import com.example.m_commerce.features.orders.data.model.GraphQLRequest
import com.example.m_commerce.features.orders.data.model.LineItem
import com.example.m_commerce.features.orders.data.model.ShippingAddress
import com.example.m_commerce.features.orders.domain.entity.CreatedOrder
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun createOrder(
        body: GraphQLRequest<DraftOrderCreateVariables>,
        token: String
    ): Flow<CreatedOrder>
}