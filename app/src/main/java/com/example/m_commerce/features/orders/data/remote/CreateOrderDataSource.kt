package com.example.m_commerce.features.orders.data.remote

import com.example.m_commerce.features.orders.domain.entity.CreatedOrder
import kotlinx.coroutines.flow.Flow

interface CreateOrderDataSource {
    suspend fun createOrder() : Flow<CreatedOrder>
}