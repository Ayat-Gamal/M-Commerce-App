package com.example.m_commerce.features.home.domain.usecases

import com.example.m_commerce.core.usecase.UseCase
import com.example.m_commerce.features.home.domain.entity.Brand
import com.example.m_commerce.features.home.domain.entity.toBrand
import com.example.m_commerce.features.home.domain.repository.BrandsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class GetBrandsUseCase @Inject constructor(private val repo: BrandsRepository) : UseCase<Unit, Flow<List<Brand>>> {
    override fun invoke(params: Unit): Flow<List<Brand>> {
        return  repo.getBrands().map { it.map { it.toBrand() } }
    }
}