package com.example.m_commerce.features.cart.domain.repo

import com.example.m_commerce.features.coupon.domain.entity.Coupon
import kotlinx.coroutines.flow.Flow

interface CouponRepository {
    suspend fun getCoupons() : Flow<List<Coupon>>
    suspend fun applyCoupon(discountCode: String) : Flow<Boolean>
}