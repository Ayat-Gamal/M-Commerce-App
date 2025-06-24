package com.example.m_commerce.features.coupon.data.repo

import com.example.m_commerce.features.coupon.data.remote.CouponRemoteDataSource
import com.example.m_commerce.features.cart.domain.repo.CouponRepository
import com.example.m_commerce.features.coupon.domain.entity.Coupon
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CouponRepositoryImpl @Inject constructor(
    private val couponRemoteDataSource: CouponRemoteDataSource
) : CouponRepository {

    override suspend fun getCoupons(): Flow<List<Coupon>> {
        return couponRemoteDataSource.getCoupons()
    }

    override suspend fun applyCoupon(discountCode: String): Flow<Boolean> {
        return couponRemoteDataSource.applyCoupon(discountCode)
    }


}
