package com.example.m_commerce.features.search.di

import com.example.m_commerce.features.search.domain.repo.ProductsRepo
import com.example.m_commerce.features.search.domain.usecases.GetProductsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetProductsUseCase(repo: ProductsRepo): GetProductsUseCase {
        return GetProductsUseCase(repo)
    }
}