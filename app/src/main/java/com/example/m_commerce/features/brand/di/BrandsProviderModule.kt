package com.example.m_commerce.features.brand.di

import com.example.m_commerce.features.brand.data.datasources.remote.BrandsRemoteDataSource
import com.example.m_commerce.features.brand.data.datasources.remote.BrandsRemoteDataSourceImpl
import com.example.m_commerce.features.brand.domain.repository.BrandsRepository
import com.example.m_commerce.features.brand.domain.usecases.GetBrandsUseCase
import com.shopify.buy3.GraphClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object BrandsProviderModule {

    @Singleton
    @Provides
    fun provideBrandsRemoteDataSource(graphClient: GraphClient): BrandsRemoteDataSource {
        return BrandsRemoteDataSourceImpl(graphClient)
    }

    @Singleton
    @Provides
    fun provideGetBrandsUseCase(repo: BrandsRepository): GetBrandsUseCase {
        return GetBrandsUseCase(repo)
    }

}