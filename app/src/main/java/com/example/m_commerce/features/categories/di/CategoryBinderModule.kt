package com.example.m_commerce.features.categories.di

import com.example.m_commerce.features.categories.data.repository.SubCategoryRepositoryImpl
import com.example.m_commerce.features.categories.domain.repository.SubCategoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CategoryBinderModule {
    @Singleton
    @Binds
    abstract fun provideCategoriesRepository(repo: SubCategoryRepositoryImpl): SubCategoryRepository

}