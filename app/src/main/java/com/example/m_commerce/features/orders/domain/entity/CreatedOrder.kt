package com.example.m_commerce.features.orders.domain.entity

data class CreatedOrder(
    val id: String,
    val number: String,
    val createdAt: String,
    val invoiceUrl: String
)