package com.example.m_commerce.features.orders.domain.usecases

import com.example.m_commerce.BuildConfig
import com.example.m_commerce.features.orders.data.model.GraphQLRequest
import com.example.m_commerce.features.orders.data.model.variables.CompleteOrderVariables
import com.example.m_commerce.features.orders.domain.entity.CompletedOrder
import com.example.m_commerce.features.orders.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CompleteOrderUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    private val token = BuildConfig.ADMIN_TOKEN

    operator fun invoke(
        body: GraphQLRequest<CompleteOrderVariables>
    ): Flow<CompletedOrder> = repository.completeOrder(body, token)
}