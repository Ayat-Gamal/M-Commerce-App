package com.example.m_commerce.features.orders.domain.entity

data class CreatedOrder(
    val id: String,
    val number: String,
    val totalAmount: String,
    val currency: String,
    val createdAt: String
)
