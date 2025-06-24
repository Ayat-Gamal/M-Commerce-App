package com.example.m_commerce.features.coupon.data.remote

import com.example.m_commerce.features.coupon.domain.entity.Coupon
import kotlinx.coroutines.flow.Flow

interface CouponRemoteDataSource {

    suspend fun getCoupons() : Flow<List<Coupon>>
    suspend fun applyCoupon(discountCode: String) : Flow<Boolean>

}