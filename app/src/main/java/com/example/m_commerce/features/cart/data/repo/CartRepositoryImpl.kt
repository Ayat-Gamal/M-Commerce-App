package com.example.m_commerce.features.cart.data.repo

import com.example.m_commerce.features.cart.data.remote.CartRemoteDataSource
import com.example.m_commerce.features.cart.domain.entity.Cart
import com.example.m_commerce.features.cart.domain.repo.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartRemoteDataSource: CartRemoteDataSource
) : CartRepository  {
    override suspend fun getCartById(): Flow<Cart> {
        return cartRemoteDataSource.getCartById()
    }

    override suspend fun updateCart(
        productVariantId: String,
        quantity: Int
    ) = cartRemoteDataSource.updateCart(
            productVariantId = productVariantId,
            quantity = quantity
        )


    override suspend fun removeProductVariant(
        productVariantId: String
    ) = cartRemoteDataSource.removeProductVariant(
            productVariantId = productVariantId
        )
    }
