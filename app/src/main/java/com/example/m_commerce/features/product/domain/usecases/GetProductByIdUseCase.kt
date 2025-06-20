package com.example.m_commerce.features.product.domain.usecases

import com.example.m_commerce.features.product.data.repo.ProductRepository
import com.example.m_commerce.features.product.presentation.ProductUiState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
    private val repo: ProductRepository
) {
    suspend operator fun invoke(productId: String): Flow<ProductUiState> {
        return repo.getProductById(productId)
    }
}