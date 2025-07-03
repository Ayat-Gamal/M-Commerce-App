package com.example.m_commerce.features.profile.domain.usecase

import com.example.m_commerce.features.profile.domain.repository.CurrencyRepository
import javax.inject.Inject

class SaveDefaultCurrencyUseCase @Inject constructor(
    private val repo: CurrencyRepository
) {
    suspend operator fun invoke(code: String) {
        repo.saveDefaultCurrencyCode(code)
    }
}