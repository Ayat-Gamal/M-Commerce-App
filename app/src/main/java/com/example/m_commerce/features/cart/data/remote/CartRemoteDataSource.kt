package com.example.m_commerce.features.cart.data.remote

import com.example.m_commerce.features.cart.domain.entity.Cart
import kotlinx.coroutines.flow.Flow

interface CartRemoteDataSource {

    suspend fun getCartById() : Flow<Cart>
    suspend fun updateCart(productVariantId: String, quantity: Int) : Flow<Boolean>
    suspend fun removeProductVariant(productVariantId: String ) : Flow<Boolean>

}