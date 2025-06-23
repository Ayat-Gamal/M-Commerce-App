package com.example.m_commerce.features.wishlist.di

import com.example.m_commerce.features.wishlist.data.remote.WishlistRemoteDataSource
import com.example.m_commerce.features.wishlist.data.remote.WishlistRemoteDataSourceImpl
import com.example.m_commerce.features.wishlist.data.repo.WishlistRepositoryImpl
import com.example.m_commerce.features.wishlist.domain.repo.WishlistRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class WishlistModule {

    @Binds
    abstract fun bindWishlistRemoteDataSource(impl: WishlistRemoteDataSourceImpl): WishlistRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindWishlistRepository(impl: WishlistRepositoryImpl): WishlistRepository
}