package com.example.m_commerce.features.home.di

import com.example.m_commerce.features.home.data.repository.BrandsRepositoryImpl
import com.example.m_commerce.features.home.domain.repository.BrandsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeBinderModule {
    @Singleton
    @Binds
    abstract fun provideBrandsRepository(remoteSource: BrandsRepositoryImpl): BrandsRepository

}