package com.example.m_commerce.features.orders.data.model


data class GraphQLRequest<T>(
    val query: String,
    val variables: T
)
