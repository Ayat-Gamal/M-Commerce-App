package com.example.m_commerce.features.profile.domain.repository

import com.example.m_commerce.features.profile.domain.model.CurrencyDetails

interface CurrencyRepository {
    suspend fun getCurrencies(): List<CurrencyDetails>

}
