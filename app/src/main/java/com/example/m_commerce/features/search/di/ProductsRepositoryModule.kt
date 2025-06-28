package com.example.m_commerce.features.search.di

import com.example.m_commerce.features.search.data.remote.ProductsRemoteDataSource
import com.example.m_commerce.features.search.data.remote.ProductsRemoteDataSourceImpl
import com.example.m_commerce.features.search.data.repo.ProductsRepoImpl
import com.example.m_commerce.features.search.domain.repo.ProductsRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProductsRepositoryModule {

    @Binds
    abstract fun bindProductsRemoteDataSource(impl: ProductsRemoteDataSourceImpl): ProductsRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindProductsRepo(impl: ProductsRepoImpl): ProductsRepo
}