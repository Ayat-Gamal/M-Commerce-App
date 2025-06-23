package com.example.m_commerce.features.orders.domain.usecases

import com.example.m_commerce.BuildConfig
import com.example.m_commerce.features.orders.data.model.DraftOrderCreateVariables
import com.example.m_commerce.features.orders.data.model.GraphQLRequest
import com.example.m_commerce.features.orders.domain.entity.CreatedOrder
import com.example.m_commerce.features.orders.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateOrderUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    private val token = BuildConfig.ADMIN_TOKEN

    operator fun invoke(
        body: GraphQLRequest<DraftOrderCreateVariables>
    ): Flow<CreatedOrder> = repository.createOrder(body, token)
}
