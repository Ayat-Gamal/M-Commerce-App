package com.example.m_commerce.features.profile.domain.usecase

import com.example.m_commerce.features.profile.domain.model.CurrencyDetails
import com.example.m_commerce.features.profile.domain.repository.CurrencyRepository

class GetCurrenciesUseCase(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke(): List<CurrencyDetails> {
        return repository.getCurrencies()
    }
}
