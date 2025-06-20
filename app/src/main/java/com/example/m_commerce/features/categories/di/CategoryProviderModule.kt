package com.example.m_commerce.features.categories.di

import com.example.m_commerce.features.categories.data.datasources.remote.CategoryRemoteDataSource
import com.example.m_commerce.features.categories.data.datasources.remote.CategoryRemoteDataSourceImpl
import com.example.m_commerce.features.categories.domain.repository.CategoryRepository
import com.example.m_commerce.features.categories.domain.usecases.GetCategoriesUseCase
import com.shopify.buy3.GraphClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CategoryProviderModule {

    @Provides
    @Singleton
    fun provideCategoryRemoteDataSource(graphClient: GraphClient): CategoryRemoteDataSource {
        return CategoryRemoteDataSourceImpl(graphClient)
    }

    @Provides
    @Singleton
    fun provideCategoryUseCase(repo: CategoryRepository): GetCategoriesUseCase {
        return GetCategoriesUseCase(repo)
    }


}