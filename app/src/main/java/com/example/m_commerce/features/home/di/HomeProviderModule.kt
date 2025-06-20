package com.example.m_commerce.features.home.di

import android.content.Context
import com.example.m_commerce.features.home.data.datasources.remote.BrandsRemoteDataSource
import com.example.m_commerce.features.home.data.datasources.remote.BrandsRemoteDataSourceImpl
import com.example.m_commerce.features.home.domain.repository.BrandsRepository
import com.example.m_commerce.features.home.domain.usecases.GetBrandsUseCase
import com.shopify.buy3.GraphClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HomeProviderModule {

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