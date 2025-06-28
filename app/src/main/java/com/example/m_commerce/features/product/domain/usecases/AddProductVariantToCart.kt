package com.example.m_commerce.features.product.domain.usecases

import com.example.m_commerce.features.product.domain.repo.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddProductVariantToCart @Inject constructor(
    private val repo: ProductRepository
) {
    operator fun invoke(productVariantId: String, quantity: Int): Flow<Boolean> {
        return repo.addProductVariantToCart(productVariantId, quantity)
    }
}