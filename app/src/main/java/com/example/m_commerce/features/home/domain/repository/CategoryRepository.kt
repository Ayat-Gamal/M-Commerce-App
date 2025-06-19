package com.example.m_commerce.features.home.domain.repository

import com.example.m_commerce.features.home.data.dto.CategoryDto
import com.example.m_commerce.features.home.domain.entity.Category

interface CategoryRepository {
    suspend fun getCategories(): List<CategoryDto>
}