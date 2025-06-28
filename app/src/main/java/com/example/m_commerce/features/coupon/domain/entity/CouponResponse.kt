package com.example.m_commerce.features.coupon.domain.entity

import com.google.gson.annotations.SerializedName

data class PriceRulesResponse(
    @SerializedName("price_rules")
    val priceRules: List<PriceRuleDto>
)

data class PriceRuleDto(
    val id: Long,
    val title: String,
    val value: String,
    val value_type: String,
    val allocation_method: String,
    val target_type: String,
    val starts_at: String?,
    val ends_at: String?,
    val usage_limit: Int?,
    val once_per_customer: Boolean?,
    val created_at: String?,
    val updated_at: String?
)


data class DiscountCodesResponse(
    @SerializedName("discount_codes")
    val discountCodes: List<DiscountCodeDto>
)

data class DiscountCodeDto(
    val id: Long,
    val price_rule_id: Long,
    val code: String,
    val usage_count: Int,
    val status: String,
    val created_at: String?,
    val updated_at: String?,
    val starts_at: String?,
    val ends_at: String?
)

