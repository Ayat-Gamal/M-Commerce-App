package com.example.m_commerce.features.product.domain.repo

import com.example.m_commerce.features.product.data.repo.ProductRepository
import com.example.m_commerce.features.product.domain.usecases.GetProductByIdUseCase
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    fun provideGetProductByIdUseCase(repo: ProductRepository): GetProductByIdUseCase {
        return GetProductByIdUseCase(repo)
    }
}