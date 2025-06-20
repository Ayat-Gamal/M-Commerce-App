package com.example.m_commerce.features.categories.di

import com.example.m_commerce.features.categories.data.repository.CategoryRepositoryImpl
import com.example.m_commerce.features.categories.domain.repository.CategoryRepository
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
    abstract fun provideCategoriesRepository(repo: CategoryRepositoryImpl): CategoryRepository

}