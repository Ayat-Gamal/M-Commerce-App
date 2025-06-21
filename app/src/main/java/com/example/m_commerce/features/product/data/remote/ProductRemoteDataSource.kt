package com.example.m_commerce.features.product.data.remote

import com.example.m_commerce.features.product.domain.entities.Product
import com.example.m_commerce.features.product.presentation.ProductUiState
import kotlinx.coroutines.flow.Flow

interface ProductRemoteDataSource {
    fun getProductById(productId: String): Flow<Product>
}