package com.example.m_commerce.features.product.presentation

data class SnackBarMessage(
    val message: String,
    val actionLabel: String? = null,
    val onAction: (() -> Unit)? = null
)

