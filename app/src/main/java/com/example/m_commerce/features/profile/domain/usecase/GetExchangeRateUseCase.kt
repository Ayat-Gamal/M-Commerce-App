package com.example.m_commerce.features.profile.domain.usecase


import com.example.m_commerce.features.profile.domain.repository.CurrencyRepository
import javax.inject.Inject

class GetExchangeRateUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {

    val exchangeRateFlow = repository.exchangeRateFlow
    suspend operator fun invoke(symbol: String): Float {
        return repository.fetchExchangeRate(symbol)
    }
}


