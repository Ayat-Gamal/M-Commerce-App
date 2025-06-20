package com.example.m_commerce.features.product.data.repo

import com.example.m_commerce.features.product.presentation.ProductUiState
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProductById(productId: String): Flow<ProductUiState>
}