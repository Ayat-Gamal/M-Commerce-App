package com.example.m_commerce.features.search.domain.usecases

import com.example.m_commerce.features.product.domain.entities.Product
import com.example.m_commerce.features.search.domain.repo.ProductsRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repo: ProductsRepo
) {
    operator fun invoke(): Flow<List<Product>> {
        return repo.getProducts()
    }
}