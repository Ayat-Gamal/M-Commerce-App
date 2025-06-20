package com.example.m_commerce.features.product.domain.repo

import com.example.m_commerce.features.product.data.remote.ProductRemoteDataSource
import com.example.m_commerce.features.product.data.repo.ProductRepository
import com.example.m_commerce.features.product.presentation.ProductUiState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProductRemoteDataSource
) : ProductRepository {
    override fun getProductById(productId: String): Flow<ProductUiState> {
        return remoteDataSource.getProductById(productId)
    }
}