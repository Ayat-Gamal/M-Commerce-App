package com.example.m_commerce.features.search.data.repo

import com.example.m_commerce.features.product.domain.entities.Product
import com.example.m_commerce.features.search.data.remote.ProductsRemoteDataSource
import com.example.m_commerce.features.search.domain.repo.ProductsRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductsRepoImpl @Inject constructor(
    private val productsRemoteDataSource: ProductsRemoteDataSource
) : ProductsRepo {
    override fun getProducts(): Flow<List<Product>> {
        return productsRemoteDataSource.getProducts()
    }
}