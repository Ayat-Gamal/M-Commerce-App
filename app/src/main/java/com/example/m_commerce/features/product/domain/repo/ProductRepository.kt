package com.example.m_commerce.features.product.domain.repo

import com.example.m_commerce.features.product.domain.entities.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProductById(productId: String): Flow<Product>
    fun addProductVariantToCart(productVariantId: String): Flow<Boolean>
}