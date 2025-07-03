package com.example.m_commerce.features.product.domain.usecases

import com.example.m_commerce.features.wishlist.domain.repo.WishlistRepository
import javax.inject.Inject

class AddToWishlistUseCase @Inject constructor(
    private val repo: WishlistRepository
) {
    suspend operator fun invoke(productVariantId: String) = repo.addToWishlist(productVariantId)
}