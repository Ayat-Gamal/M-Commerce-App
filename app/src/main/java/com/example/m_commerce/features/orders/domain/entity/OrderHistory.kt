package com.example.m_commerce.features.orders.domain.entity

import com.example.m_commerce.features.orders.data.model.variables.LineItem

data class OrderHistory(
    val id: String,
    val shippedTo: String,
    val totalPrice: String,
    val items: List<LineItem>,
)
