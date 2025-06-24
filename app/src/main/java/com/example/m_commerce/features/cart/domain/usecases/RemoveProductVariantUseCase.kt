package com.example.m_commerce.features.cart.domain.usecases

import com.example.m_commerce.features.cart.domain.repo.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoveProductVariantUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke( productVariantId: String,) : Flow<Boolean> {
        return cartRepository.removeProductVariant( productVariantId)
    }
}