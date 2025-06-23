package com.example.m_commerce.features.brand.data.datasources.remote

import com.example.m_commerce.features.brand.data.dto.BrandDto
import kotlinx.coroutines.flow.Flow

interface BrandsRemoteDataSource {
    fun getBrands(first: Int): Flow<List<BrandDto>>
}