package com.example.m_commerce.features.orders.data.model

import com.example.m_commerce.features.orders.domain.entity.CreatedOrder
import kotlinx.serialization.SerialName

data class OrderResponse(
    val id: String,
    val name: String,
    val createdAt: String,
    val invoiceUrl: String
)



data class TotalPriceSet(
    val shopMoney: ShopMoney
)

data class ShopMoney(
    val amount: String,
    val currencyCode: String
)


fun OrderResponse.toDomain(): CreatedOrder {
    return CreatedOrder(
        id = this.id,
        number = this.name,
        createdAt = this.createdAt,
        invoiceUrl = this.invoiceUrl
    )
}



data class DraftOrderCreateVariables(
    val email: String,
    val shippingAddress: ShippingAddress,
    val lineItems: List<LineItem>,
    val note: String? = null
)

data class ShippingAddress(
    val firstName: String,
    val lastName: String,
    val address1: String,
    val city: String,
    val country: String,
    val zip: String
)

data class LineItem(
    val variantId: String,
    val quantity: Int,
    val originalUnitPrice: String
)



data class CompleteOrderVariables(
    val id: String
)











