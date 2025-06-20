package com.example.m_commerce.features.product.domain.entities

import com.shopify.buy3.Storefront

data class Product(
    val id: String,
    val title: String,
    val description: String,
    val images: List<String>,
    val price: String,
    val category: String,
    val currencyCode: String,
    val colors: List<String>,
    val sizes: List<String>,
    var variants: List<Storefront. ProductVariant>
)
