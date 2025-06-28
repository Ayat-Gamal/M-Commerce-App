package com.example.m_commerce.features.product.data.repo

import com.example.m_commerce.features.product.data.remote.ProductRemoteDataSource
import com.example.m_commerce.features.product.domain.entities.Product
import com.example.m_commerce.features.product.domain.repo.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProductRemoteDataSource
) : ProductRepository {
    override fun getProductById(productId: String): Flow<Product> {
        return remoteDataSource.getProductById(productId)
    }

    override fun addProductVariantToCart(productVariantId: String, quantity: Int): Flow<Boolean> {
        return remoteDataSource.addProductVariantToCart(productVariantId, quantity)
    }
}