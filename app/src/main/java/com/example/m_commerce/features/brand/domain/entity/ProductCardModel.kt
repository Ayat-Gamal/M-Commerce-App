package com.example.m_commerce.features.brand.domain.entity

data class ProductCardModel(
    val id: String,
    val name: String,
    val image: String,
    val price: Double,
    val offer: Double? = null
)
