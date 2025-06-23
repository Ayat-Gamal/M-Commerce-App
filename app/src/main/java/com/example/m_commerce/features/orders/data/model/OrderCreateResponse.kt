package com.example.m_commerce.features.orders.data.model

import com.shopify.buy3.Storefront

data class OrderCreateResponse(
    val data: OrderCreateData?
)

data class OrderCreateData(
    val draftOrderCreate: DraftOrderCreateResult?
)

data class DraftOrderCreateResult(
    val draftOrder: OrderResponse?,
    val userErrors: List<UserError>?
)



