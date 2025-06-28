package com.example.m_commerce.features.orders.domain.entity

import com.example.m_commerce.features.orders.data.model.variables.LineItem
import java.util.Date

data class OrderHistory(
    val id: String,
    val shippedTo: String,
    val totalPrice: String,
    val status: String,
    val createdAt: String,
    val currencyCode: String,
    val items: List<LineItem>,
)
