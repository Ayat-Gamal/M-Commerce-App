package com.example.m_commerce.features.product.domain.usecases

import com.example.m_commerce.features.product.domain.repo.ProductRepository
import com.example.m_commerce.features.product.domain.entities.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
    private val repo: ProductRepository
) {
    suspend operator fun invoke(productId: String): Flow<Product> {
        return repo.getProductById(productId)
    }
}