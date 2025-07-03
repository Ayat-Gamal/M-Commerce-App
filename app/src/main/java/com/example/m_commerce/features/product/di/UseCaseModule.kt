package com.example.m_commerce.features.product.di

import com.example.m_commerce.features.product.domain.repo.ProductRepository
import com.example.m_commerce.features.product.domain.usecases.AddProductVariantToCart
import com.example.m_commerce.features.product.domain.usecases.GetProductByIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideGetProductByIdUseCase(repo: ProductRepository): GetProductByIdUseCase {
        return GetProductByIdUseCase(repo)
    }

    @Provides
    fun provideAddProductVariantToCart(repo: ProductRepository): AddProductVariantToCart {
        return AddProductVariantToCart(repo)
    }
}