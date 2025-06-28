package com.example.m_commerce.features.cart.di

import com.example.m_commerce.features.cart.domain.repo.CartRepository
import com.example.m_commerce.features.cart.domain.usecases.GetCartByIdUseCase
import com.example.m_commerce.features.cart.domain.usecases.RemoveProductVariantUseCase
import com.example.m_commerce.features.cart.domain.usecases.UpdateCartUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CartModule {

    @Provides
    fun provideGetCartByIdUseCase(cartRepository: CartRepository): GetCartByIdUseCase {
        return GetCartByIdUseCase(cartRepository)
    }
    @Provides
    fun provideUpdateCartUseCase(cartRepository: CartRepository): UpdateCartUseCase {
        return UpdateCartUseCase(cartRepository)
    }

    @Provides
    fun provideRemoveProductVariantUseCase(cartRepository: CartRepository): RemoveProductVariantUseCase {
        return RemoveProductVariantUseCase(cartRepository)
    }

}