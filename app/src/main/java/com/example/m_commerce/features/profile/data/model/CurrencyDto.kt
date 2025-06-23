package com.example.m_commerce.features.profile.data.model


data class CurrencyApiResponse(
    val supportedCurrenciesMap: Map<String, CurrencyDto>
)

data class CurrencyDto(
    val currencyCode: String?,
    val currencyName: String?,
    val countryCode: String?,
    val countryName: String?,
    val status: String?,
    val availableFrom: String?,
    val availableUntil: String?,
    val icon: String?
)
