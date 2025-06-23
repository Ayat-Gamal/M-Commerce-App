package com.example.m_commerce.features.profile.domain.usecase

import com.example.m_commerce.features.profile.domain.model.CurrencyDetails
import com.example.m_commerce.features.profile.domain.repository.CurrencyPreferencesRepository
import javax.inject.Inject

class GetDefaultCurrencyUseCase @Inject constructor  (private val repository: CurrencyPreferencesRepository) {
    suspend operator fun invoke(): CurrencyDetails? {
        return repository.getCurrencyDetails()
    }
}