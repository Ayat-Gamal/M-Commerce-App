package com.example.m_commerce.features.wishlist.data.repo

import com.example.m_commerce.features.wishlist.data.remote.WishlistRemoteDataSource
import com.example.m_commerce.features.wishlist.domain.repo.WishlistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WishlistRepositoryImpl @Inject constructor(
    private val firestoreDataSource: WishlistRemoteDataSource
) : WishlistRepository {
    override suspend fun addToWishlist(productVariantId: String): Flow<String> {
        return firestoreDataSource.addToWishlist(productVariantId)
    }

    override suspend fun deleteFromWishlist(productVariantId: String) =
        firestoreDataSource.deleteFromWishlist(productVariantId)

    override suspend fun isInWishlist(productVariantId: String): Flow<Boolean> {
        return firestoreDataSource.isInWishlist(productVariantId)
    }

    override suspend fun getWishlist(): Flow<List<String>> {
        return firestoreDataSource.getWishlist()
    }
}