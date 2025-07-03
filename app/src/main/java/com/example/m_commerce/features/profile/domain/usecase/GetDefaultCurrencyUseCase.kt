package com.example.m_commerce.features.profile.domain.usecase

import com.example.m_commerce.features.profile.domain.repository.CurrencyRepository
import javax.inject.Inject

class GetDefaultCurrencyUseCase @Inject constructor(
    private val repo: CurrencyRepository
) {
    operator fun invoke(): String? = repo.getDefaultCurrencyCode()
}