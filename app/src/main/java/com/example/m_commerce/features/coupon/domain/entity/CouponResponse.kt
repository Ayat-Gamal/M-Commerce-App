package com.example.m_commerce.features.coupon.domain.entity

import com.google.gson.annotations.SerializedName

//data class DiscountCodesResponse(
//    @SerializedName("discount_codes")
//    val discountCodes: List<DiscountCodeDto>
//)
//data class DiscountCodeDto(
//    val id: Long,
//    val code: String,
//    val usage_count: Int,
//    val status: String?
//) {
//    fun toDomain(): Coupon = Coupon(
//        id = id,
//        code = code,
//        usageCount = usage_count,
//        status = status ?: ""
//    )
//}


data class PriceRulesResponse(
    @SerializedName("price_rules")
    val priceRules: List<PriceRuleDto>
)

data class PriceRuleDto(
    val id: Long,
    val title: String
)

data class DiscountCodesResponse(
    @SerializedName("discount_codes")
    val discountCodes: List<DiscountCodeDto>
)

data class DiscountCodeDto(
    val id: Long,
    val code: String,
    val usage_count: Int,
    val status: String
)
