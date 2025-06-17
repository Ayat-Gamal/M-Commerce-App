package com.example.m_commerce.features.brand.domain.entity

data class ProductCardModel(
    val id: Int,
    val name: String,
    val image: String,
    val price: Double,
    val offer: Double? = null
)
