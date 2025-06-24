package com.example.m_commerce.features.orders.data.model

import com.example.m_commerce.features.orders.domain.entity.CompletedOrder
import com.example.m_commerce.features.orders.domain.entity.CreatedOrder
import kotlinx.serialization.SerialName

data class OrderResponse(
    val id: String,
    val name: String,
    val createdAt: String,
    val invoiceUrl: String
)

fun OrderResponse.toDomain(): CreatedOrder = CreatedOrder(
    id = id,
    number = name,
    createdAt = createdAt,
    invoiceUrl = invoiceUrl
)



//data class TotalPriceSet(
//    val shopMoney: ShopMoney
//)
//
//data class ShopMoney(
//    val amount: String,
//    val currencyCode: String
//)





















