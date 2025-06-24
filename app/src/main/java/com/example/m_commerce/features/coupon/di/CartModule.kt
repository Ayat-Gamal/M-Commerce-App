package com.example.m_commerce.features.coupon.di

import com.example.m_commerce.features.cart.domain.repo.CouponRepository
import com.example.m_commerce.features.coupon.domain.usecases.ApplyCouponUseCase
import com.example.m_commerce.features.coupon.domain.usecases.GetCouponsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CartModule {

    @Provides
    fun provideApplyCouponUUseCase(couponRepository: CouponRepository): ApplyCouponUseCase {
        return ApplyCouponUseCase(couponRepository)
    }
    @Provides
    fun provideGetCouponsUseCase(couponRepository: CouponRepository): GetCouponsUseCase {
        return GetCouponsUseCase(
            couponRepository = couponRepository
        )
    }


}