package com.example.m_commerce.features.coupon.domain.usecases

import com.example.m_commerce.BuildConfig
import com.example.m_commerce.features.cart.domain.repo.CouponRepository
import com.example.m_commerce.features.coupon.domain.entity.Coupon
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCouponsUseCase @Inject constructor(private val couponRepository: CouponRepository) {
    private val token = BuildConfig.ADMIN_TOKEN

    suspend operator fun invoke() : Flow<List<Coupon>> {
        return couponRepository.getCoupons(token)
    }
}



//package com.example.m_commerce.features.cart.domain.usecases
//
//import com.example.m_commerce.features.cart.domain.repo.CartRepository
//import kotlinx.coroutines.flow.Flow
//import javax.inject.Inject
//
//class RemoveProductVariantUseCase @Inject constructor(
//    private val cartRepository: CartRepository
//) {
//    suspend operator fun invoke( productVariantId: String,) : Flow<Boolean> {
//        return cartRepository.removeProductVariant( productVariantId)
//    }
//}