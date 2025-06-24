package com.example.m_commerce.features.orders.data.model.variables

import com.shopify.buy3.Storefront

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


fun Storefront. MailingAddress.toDomain() = ShippingAddress(
    firstName = firstName,
    lastName = lastName,
    address1 = address1,
    city = city,
    country = country,
    zip = zip
)


data class LineItem(
    val variantId: String,
    val quantity: Int,
    val originalUnitPrice: String
)
