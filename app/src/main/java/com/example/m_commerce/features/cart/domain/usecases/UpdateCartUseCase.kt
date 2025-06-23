package com.example.m_commerce.features.cart.domain.usecases

import com.example.m_commerce.features.cart.domain.repo.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke( productVariantId: String, quantity: Int) : Flow<Boolean> {
        return cartRepository.updateCart(
            productVariantId,
            quantity = quantity
        )
    }
}