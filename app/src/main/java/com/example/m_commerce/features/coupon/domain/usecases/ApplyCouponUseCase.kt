package com.example.m_commerce.features.coupon.domain.usecases

import com.example.m_commerce.features.cart.domain.repo.CouponRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ApplyCouponUseCase @Inject constructor(private val couponRepository: CouponRepository) {
    suspend operator fun invoke(discountCode: String): Flow<Boolean> {
        return couponRepository.applyCoupon(
            discountCode = discountCode
        )
    }
}
