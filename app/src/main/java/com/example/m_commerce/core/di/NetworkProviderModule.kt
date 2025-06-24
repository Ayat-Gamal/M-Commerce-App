package com.example.m_commerce.core.di

import android.content.Context
import com.example.m_commerce.BuildConfig
import com.example.m_commerce.features.orders.data.remote.ShopifyAdminApiService
import com.shopify.buy3.GraphClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

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

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder().baseUrl("https://mad45-alex-and02.myshopify.com/admin/api/2024-04/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideShopifyApiService(retrofit: Retrofit): ShopifyAdminApiService = retrofit.create(ShopifyAdminApiService::class.java)
}