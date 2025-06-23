package com.example.m_commerce.features.profile.domain.model

data class CurrencyDetails(
    val currencyCode: String,
    val currencyName: String,
    val countryCode: String,
    val countryName: String,
    val status: String,
    val availableUntil: String,
    val icon: String
)