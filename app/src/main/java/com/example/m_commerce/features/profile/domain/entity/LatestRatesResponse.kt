package com.example.m_commerce.features.profile.domain.entity

data class LatestRatesResponse(
    val success: Boolean,
    val base: String,
//    val date: String,
    val rates: Map<String, Double>
)
