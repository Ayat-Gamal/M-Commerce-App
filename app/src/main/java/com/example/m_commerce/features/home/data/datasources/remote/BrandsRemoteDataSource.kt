package com.example.m_commerce.features.home.data.datasources.remote

import com.example.m_commerce.features.home.data.dto.BrandDto
import com.example.m_commerce.features.home.domain.entity.Brand
import kotlinx.coroutines.flow.Flow

interface BrandsRemoteDataSource {
    fun getBrands(): Flow<List<BrandDto>>
}