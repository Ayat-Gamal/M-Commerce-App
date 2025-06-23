package com.example.m_commerce.features.cart.domain.entity

import ProductVariant
import com.shopify.buy3.Storefront
import com.shopify.graphql.support.ID

data class Cart(
    val id: ID,
    val createdAt: String,
    val updatedAt: String,
    val lines: List<ProductVariant>,
    val totalAmount: String,
    val subtotalAmount: String,
    val totalTaxAmount: String?,
    val totalDutyAmount: String?,
    val currency: String
) {
    companion object {
        fun fromGraphQL(data: Storefront.Cart): Cart {
            val variants = data.lines.edges.mapNotNull {
                val node = it.node
                val merchandise = node.merchandise as? Storefront.ProductVariant
                val cartLineId = node.id.toString()

                merchandise?.let { m ->
                    ProductVariant(
                        id = m.id.toString(),
                        lineId = cartLineId,
                        title = m.title,
                        quantity = node.quantity,
                        price = m.price.amount,
                        currency = m.price.currencyCode.name,
                        imageUrl = m.image?.url,
                        imageAlt = m.image?.altText,
                        productTitle = m.product.title,
                    )
                }
            }

            return Cart(
                id = data.id,
                createdAt = data.createdAt.toString(),
                updatedAt = data.updatedAt.toString(),
                lines = variants,
                totalAmount = data.cost.totalAmount.amount,
                subtotalAmount = data.cost.subtotalAmount.amount,
                totalTaxAmount = data.cost.totalTaxAmount?.amount,
                totalDutyAmount = data.cost.totalDutyAmount?.amount,
                currency = data.cost.totalAmount.currencyCode.name
            )
        }
    }
}

