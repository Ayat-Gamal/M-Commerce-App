package com.example.m_commerce.features.orders.data.model

import com.example.m_commerce.features.orders.domain.entity.CompletedOrder


data class CompleteOrderWrapper(
    val data: CompleteOrderData?
)

data class CompleteOrderData(
    val draftOrderComplete: DraftOrderCompleteResult?
)

data class DraftOrderCompleteResult(
    val draftOrder: CompleteOrderResponse?,
    val userErrors: List<UserError>?
)

data class CompleteOrderResponse(
    val id: String,
    val name: String,
    val status: String
)


fun CompleteOrderResponse.toDomain(): CompletedOrder = CompletedOrder(
    id = id,
    name = name,
    status = status
)
