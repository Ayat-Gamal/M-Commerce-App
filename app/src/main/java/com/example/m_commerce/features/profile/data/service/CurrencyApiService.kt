package com.example.m_commerce.features.profile.data.service

import com.example.m_commerce.features.profile.data.model.CurrencyApiResponse
import com.example.m_commerce.features.profile.domain.model.CurrencyRatesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApiService {
    @GET("v2.0/supported-currencies")
    suspend fun getSupportedCurrencies(): CurrencyApiResponse

    @GET("v2.0/rates/latest")
    suspend fun getExchangeRate(
        @Query("apikey") apiKey: String,
        @Query("symbols") symbols: String = "USD",
        @Query("base") base: String = "EGY"
    ): CurrencyRatesResponse

}
