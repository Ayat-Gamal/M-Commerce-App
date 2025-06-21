package com.example.m_commerce.features.profile.data.reposetoryImpl

import android.util.Log
import com.example.m_commerce.features.profile.data.service.CurrencyApiService
import com.example.m_commerce.features.profile.domain.model.CurrencyDetails
import com.example.m_commerce.features.profile.domain.repository.CurrencyRepository
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val apiService: CurrencyApiService
) : CurrencyRepository {

    override suspend fun getCurrencies(): List<CurrencyDetails> {
        return try {
            val response = apiService.getSupportedCurrencies()
            Log.d(
                "CurrencyRepository",
                "API Response Success: ${response.supportedCurrenciesMap.keys}"
            )

            response.supportedCurrenciesMap.mapNotNull { (_, dto) ->
                try {
                    val availableUntil = dto.availableUntil ?: return@mapNotNull null
                    CurrencyDetails(
                        currencyCode = dto.currencyCode ?: return@mapNotNull null,
                        currencyName = dto.currencyName ?: "Unknown",
                        countryCode = dto.countryCode ?: "N/A",
                        countryName = dto.countryName ?: "N/A",
                        status = dto.status ?: "Unknown",
                        availableUntil = availableUntil,
                        icon = dto.icon ?: ""
                    )
                } catch (e: Exception) {
                    Log.e("CurrencyRepository", "Mapping error for dto: $dto", e)
                    null
                }
            }
        } catch (e: Exception) {
            Log.e("CurrencyRepository", "Error fetching currencies", e)
            emptyList()
        }
    }
}
