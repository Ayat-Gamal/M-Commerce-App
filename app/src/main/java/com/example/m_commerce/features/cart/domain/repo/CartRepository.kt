package com.example.m_commerce.features.cart.domain.repo

import com.example.m_commerce.features.cart.domain.entity.Cart
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun getCartById(id: String) : Flow<Cart>
    suspend fun updateCart( productVariantId: String , quantity: Int) : Flow<Boolean>
    suspend fun removeProductVariant(productVariantId: String ) : Flow<Boolean>
}