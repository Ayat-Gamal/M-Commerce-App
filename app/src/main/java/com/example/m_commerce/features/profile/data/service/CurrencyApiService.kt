package com.example.m_commerce.features.profile.data.service

import com.example.m_commerce.features.profile.data.model.CurrencyApiResponse
import retrofit2.http.GET

interface CurrencyApiService {
    @GET("v2.0/supported-currencies")
    suspend fun getSupportedCurrencies(): CurrencyApiResponse
}
