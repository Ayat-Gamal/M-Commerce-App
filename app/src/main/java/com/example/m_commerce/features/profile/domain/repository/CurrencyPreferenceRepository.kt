package com.example.m_commerce.features.profile.domain.repository

import com.example.m_commerce.features.profile.domain.model.CurrencyDetails

interface CurrencyPreferencesRepository {
    suspend fun saveCurrencyDetails(details: CurrencyDetails)
    suspend fun getCurrencyDetails(): CurrencyDetails?
}