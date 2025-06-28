package com.example.m_commerce.features.profile.domain.entity

data class ApiConversionRateResponse(
    val result: String,
    val base_code: String,
    val target_code: String,
    val conversion_rate: Double
)
