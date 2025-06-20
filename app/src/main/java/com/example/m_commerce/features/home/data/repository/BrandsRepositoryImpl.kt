package com.example.m_commerce.features.home.data.repository

import com.example.m_commerce.features.home.data.datasources.remote.BrandsRemoteDataSource
import com.example.m_commerce.features.home.data.dto.BrandDto
import com.example.m_commerce.features.home.domain.entity.Brand
import com.example.m_commerce.features.home.domain.repository.BrandsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class BrandsRepositoryImpl @Inject constructor(private val remoteSource: BrandsRemoteDataSource): BrandsRepository {
    override fun getBrands(): Flow<List<BrandDto>>  = remoteSource.getBrands()
}