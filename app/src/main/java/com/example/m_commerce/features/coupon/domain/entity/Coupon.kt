package com.example.m_commerce.features.coupon.domain.entity

data class Coupon(
    val id: Long,
    val code: String,
    val usageCount: Int,
    val status: String,
    val summary:String
)
