package com.example.m_commerce.features.categories.data.repository

import com.example.m_commerce.features.categories.data.datasources.remote.CategoryRemoteDataSource
import com.example.m_commerce.features.categories.domain.repository.CategoryRepository
import com.example.m_commerce.features.categories.data.dto.CategoryDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(private val remoteSource: CategoryRemoteDataSource) : CategoryRepository {
    override fun getCategories(): Flow<List<CategoryDto>> {
        return remoteSource.getCategories()
    }
}