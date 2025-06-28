package com.example.m_commerce.features.cart.di

import com.example.m_commerce.features.cart.data.remote.CartRemoteDataSource
import com.example.m_commerce.features.cart.data.remote.CartRemoteDataSourceImpl
import com.example.m_commerce.features.cart.data.repo.CartRepositoryImpl
import com.example.m_commerce.features.cart.domain.repo.CartRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    abstract fun bindCartRemoteDataSource(impl: CartRemoteDataSourceImpl): CartRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindCartRepository(impl: CartRepositoryImpl): CartRepository

}