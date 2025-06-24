package com.example.m_commerce.features.profile.domain.usecase

import android.util.Log
import com.example.m_commerce.features.profile.domain.repository.CurrencyRepository
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(
    private val repo: CurrencyRepository
) {
    suspend operator fun invoke(): Map<String, String> {
        return repo.getSupportedCurrencies()
        Log.i("TAG", "invoke:yes we are here ")
    }
}