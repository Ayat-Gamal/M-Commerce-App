package com.example.m_commerce.features.search.data.remote

import com.example.m_commerce.features.product.domain.entities.Product
import kotlinx.coroutines.flow.Flow

interface ProductsRemoteDataSource {
    fun getProducts(): Flow<List<Product>>
}