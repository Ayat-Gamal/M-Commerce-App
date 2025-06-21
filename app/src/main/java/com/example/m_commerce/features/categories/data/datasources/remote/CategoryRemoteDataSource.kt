package com.example.m_commerce.features.categories.data.datasources.remote

import com.example.m_commerce.features.categories.data.dto.CategoryDto
import kotlinx.coroutines.flow.Flow

interface CategoryRemoteDataSource {
    fun getCategories(): Flow<List<CategoryDto>>
}

