package com.example.m_commerce.features.product.di

import com.example.m_commerce.features.product.data.remote.ProductRemoteDataSource
import com.example.m_commerce.features.product.data.remote.ProductRemoteDataSourceImpl
import com.example.m_commerce.features.product.domain.repo.ProductRepository
import com.example.m_commerce.features.product.data.repo.ProductRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProductRepositoryModule {

    @Binds
    abstract fun bindProductRemoteDataSource(impl: ProductRemoteDataSourceImpl): ProductRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindProductRepository(impl: ProductRepositoryImpl): ProductRepository
}