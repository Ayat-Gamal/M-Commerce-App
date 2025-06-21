package com.example.m_commerce.features.wishlist.di

import com.example.m_commerce.features.wishlist.domain.repo.WishlistRepository
import com.example.m_commerce.features.wishlist.domain.usecases.DeleteFromWishlistUseCase
import com.example.m_commerce.features.wishlist.domain.usecases.GetWishlistUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class WishlistUseCaseModule {

    @Provides
    fun provideGetWishlistUseCase(repo: WishlistRepository) = GetWishlistUseCase(repo)

    @Provides
    fun provideDeleteFromWishlistUseCase(repo: WishlistRepository) = DeleteFromWishlistUseCase(repo)
}