package com.example.m_commerce.features.categories.di

import com.example.m_commerce.features.categories.data.datasources.remote.SubCategoryRemoteDataSource
import com.example.m_commerce.features.categories.data.datasources.remote.SubSubCategoryRemoteDataSourceImpl
import com.example.m_commerce.features.categories.domain.repository.SubCategoryRepository
import com.example.m_commerce.features.categories.domain.usecases.GetSubCategoriesUseCase
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
    fun provideCategoryRemoteDataSource(graphClient: GraphClient): SubCategoryRemoteDataSource {
        return SubSubCategoryRemoteDataSourceImpl(graphClient)
    }

    @Provides
    @Singleton
    fun provideCategoryUseCase(repo: SubCategoryRepository): GetSubCategoriesUseCase {
        return GetSubCategoriesUseCase(repo)
    }


}