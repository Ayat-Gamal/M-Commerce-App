package com.example.m_commerce.features.wishlist.data.remote

import kotlinx.coroutines.flow.Flow

interface WishlistRemoteDataSource {
    suspend fun addToWishlist(productVariantId: String)
    suspend fun deleteFromWishlist(productVariantId: String)
    suspend fun isInWishlist(productVariantId: String): Flow<Boolean>
    suspend fun getWishlist(): Flow<List<String>>
}