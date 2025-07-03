package com.example.m_commerce.features.profile.domain.repository

import kotlinx.coroutines.flow.StateFlow

interface CurrencyRepository {
    val exchangeRateFlow: StateFlow<Float>
    suspend fun fetchExchangeRate(symbol: String): Float
    fun getCachedExchangeRate(): Float
    suspend fun getSupportedCurrencies(): Map<String, String>
    fun getDefaultCurrencyCode(): String?
    suspend fun saveDefaultCurrencyCode(code: String)
}