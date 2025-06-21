package com.example.m_commerce.features.product.domain.usecases

import com.example.m_commerce.features.wishlist.domain.repo.WishlistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckIfInWishlistUseCase @Inject constructor(
    private val repo: WishlistRepository
) {
    suspend operator fun invoke(productVariantId: String): Flow<Boolean> {
        return repo.isInWishlist(productVariantId)
    }
}