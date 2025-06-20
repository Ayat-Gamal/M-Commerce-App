package com.example.m_commerce.features.home.domain.repository

import com.example.m_commerce.features.home.data.dto.CategoryDto

interface CategoriesRepository {
    suspend fun getCategories(): List<CategoryDto>
}