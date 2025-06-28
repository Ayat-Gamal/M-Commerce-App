package com.example.m_commerce.features.brand.domain.usecases

import com.example.m_commerce.core.usecase.UseCase
import com.example.m_commerce.features.brand.domain.entity.Brand
import com.example.m_commerce.features.brand.domain.entity.toBrand
import com.example.m_commerce.features.brand.domain.repository.BrandsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class GetBrandsUseCase @Inject constructor(private val repo: BrandsRepository) : UseCase<Int, Flow<List<Brand>?>> {
    override fun invoke(params: Int): Flow<List<Brand>?> {
        return  repo.getBrands(params).map { it.map { it.toBrand() } }
    }
}