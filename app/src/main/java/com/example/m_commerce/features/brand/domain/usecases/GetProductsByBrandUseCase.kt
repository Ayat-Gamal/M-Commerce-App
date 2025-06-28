package com.example.m_commerce.features.brand.domain.usecases

import com.example.m_commerce.core.usecase.UseCase
import com.example.m_commerce.features.brand.domain.repository.BrandsRepository
import com.example.m_commerce.features.product.domain.entities.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsByBrandUseCase @Inject constructor(private val repo: BrandsRepository): UseCase<String, Flow<List<Product>?>> {
    override fun invoke(params: String): Flow<List<Product>?> {
        return repo.getProductsByBrandName(params)
    }
}