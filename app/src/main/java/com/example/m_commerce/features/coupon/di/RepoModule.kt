package com.example.m_commerce.features.coupon.di

import com.example.m_commerce.features.cart.domain.repo.CouponRepository
import com.example.m_commerce.features.coupon.data.remote.CouponRemoteDataSource
import com.example.m_commerce.features.coupon.data.remote.CouponRemoteDataSourceImpl
import com.example.m_commerce.features.coupon.data.repo.CouponRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class CouponModule {

    @Binds
    abstract fun bindCartRemoteDataSource(impl: CouponRemoteDataSourceImpl): CouponRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindCouponRepository(impl: CouponRepositoryImpl): CouponRepository

}