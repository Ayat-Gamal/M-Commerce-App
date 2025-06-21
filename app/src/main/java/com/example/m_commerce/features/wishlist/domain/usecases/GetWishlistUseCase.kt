package com.example.m_commerce.features.wishlist.domain.usecases

import com.example.m_commerce.features.wishlist.domain.repo.WishlistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWishlistUseCase @Inject constructor(
    private val repo: WishlistRepository
) {
    suspend operator fun invoke(): Flow<List<String>> {
        return repo.getWishlist()
    }
}