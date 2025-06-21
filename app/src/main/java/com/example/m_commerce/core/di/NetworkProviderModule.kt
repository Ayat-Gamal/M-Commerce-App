package com.example.m_commerce.core.di

import android.content.Context
import com.shopify.buy3.GraphClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.example.m_commerce.BuildConfig

@Module
@InstallIn(SingletonComponent::class)
object NetworkProviderModule {

    @Singleton
    @Provides
    fun provideShopifyClient(@ApplicationContext context: Context): GraphClient {
        val shopDomain = BuildConfig.SHOP_DOMAIN
        val accessToken = BuildConfig.ACCESS_TOKEN
        return GraphClient
            .build(
                context = context,
                shopDomain = shopDomain,
                accessToken = accessToken,
            )
    }
}