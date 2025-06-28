package com.example.m_commerce.features.brand.di

import com.example.m_commerce.features.brand.data.repository.BrandsRepositoryImpl
import com.example.m_commerce.features.brand.domain.repository.BrandsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BrandsBinderModule {
    @Singleton
    @Binds
    abstract fun provideBrandsRepository(repo: BrandsRepositoryImpl): BrandsRepository

}