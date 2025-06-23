package com.example.m_commerce.features.profile.domain.model

data class CurrencyRatesResponse(
    val date: String,
    val base: String,
    val rates: Map<String, String>
)
