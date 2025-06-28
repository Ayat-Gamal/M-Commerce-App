package com.example.m_commerce.features.categories.data.repository

import com.example.m_commerce.features.categories.data.datasources.remote.SubCategoryRemoteDataSource
import com.example.m_commerce.features.categories.domain.repository.SubCategoryRepository
import com.example.m_commerce.features.categories.data.dto.CategoryDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubCategoryRepositoryImpl @Inject constructor(private val remoteSource: SubCategoryRemoteDataSource) : SubCategoryRepository {
    override fun getCategories(): Flow<List<CategoryDto>> {
        return remoteSource.getCategories()
    }
}