package com.example.m_commerce.features.orders.data.remote

import com.example.m_commerce.features.orders.data.model.DraftOrderCreateVariables
import com.example.m_commerce.features.orders.data.model.GraphQLRequest
import com.example.m_commerce.features.orders.data.model.toDomain
import com.example.m_commerce.features.orders.domain.entity.CreatedOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class OrderDataSourceImpl @Inject constructor(
    private val service: ShopifyAdminApiService
) : OrderDataSource {
    override fun createOrder(
        body: GraphQLRequest<DraftOrderCreateVariables>,
        token: String
    ): Flow<CreatedOrder> = flow {
        val response = service.createOrder(body, token)
        if (response.isSuccessful) {
            val order = response.body()?.data?.draftOrderCreate?.draftOrder
            if (order != null) {
                emit(order.toDomain())
            } else {
                throw Exception("Draft order creation failed: Empty response")
            }
        } else {
            val errorBody = response.errorBody()?.string()
            throw Exception("HTTP ${response.code()}: $errorBody")
        }
    }.flowOn(Dispatchers.IO)
}