package com.example.m_commerce.features.cart.domain.usecases

import com.example.m_commerce.features.cart.domain.entity.Cart
import com.example.m_commerce.features.cart.domain.repo.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartByIdUseCase @Inject constructor(
    private val cartRepository: CartRepository
)  {
    suspend operator fun invoke(id: String) : Flow<Cart> {
        return cartRepository.getCartById(id)
    }
}