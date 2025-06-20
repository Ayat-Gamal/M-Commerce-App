package com.example.m_commerce.features.home.domain.repository

import com.example.m_commerce.features.home.data.dto.BrandDto
import com.example.m_commerce.features.home.data.dto.CategoryDto
import com.example.m_commerce.features.home.domain.entity.Brand
import com.example.m_commerce.features.home.domain.entity.Category
import kotlinx.coroutines.flow.Flow

interface BrandsRepository {
    fun getBrands(): Flow<List<BrandDto>>
}