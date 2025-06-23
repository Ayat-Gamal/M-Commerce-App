package com.example.m_commerce.features.orders.data.di

import com.example.m_commerce.features.orders.data.remote.OrderDataSource
import com.example.m_commerce.features.orders.data.remote.OrderDataSourceImpl
import com.example.m_commerce.features.orders.data.remote.ShopifyAdminApiService
import com.example.m_commerce.features.orders.data.repository.OrderRepositoryImpl
import com.example.m_commerce.features.orders.domain.repository.OrderRepository
import com.example.m_commerce.features.orders.domain.usecases.CreateOrderUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OrderProviderModule {


    @Singleton
    @Provides
    fun provideOrderDataSource(service: ShopifyAdminApiService): OrderDataSource = OrderDataSourceImpl(service)

    @Singleton
    @Provides
    fun provideOrderRepository(dataSource: OrderDataSource): OrderRepository = OrderRepositoryImpl(dataSource)

    @Singleton
    @Provides
    fun provideCreateOrderUseCase(repository: OrderRepository): CreateOrderUseCase = CreateOrderUseCase(repository)
}