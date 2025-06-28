package com.example.m_commerce.features.brand.data.repository

import com.example.m_commerce.features.brand.data.datasources.remote.BrandsRemoteDataSource
import com.example.m_commerce.features.brand.data.dto.BrandDto
import com.example.m_commerce.features.brand.domain.repository.BrandsRepository
import com.example.m_commerce.features.product.domain.entities.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class BrandsRepositoryImpl @Inject constructor(private val remoteSource: BrandsRemoteDataSource): BrandsRepository {
    override fun getBrands(first: Int): Flow<List<BrandDto>>  = remoteSource.getBrands(first)
    override fun getProductsByBrandName(name: String): Flow<List<Product>> = remoteSource.getProductsByBrandName(name)
}