package com.example.m_commerce.features.search.domain.repo

import com.example.m_commerce.features.product.domain.entities.Product
import kotlinx.coroutines.flow.Flow

interface ProductsRepo {
    fun getProducts(): Flow<List<Product>>
}