package com.example.m_commerce.features.profile.data.reposetoryImpl

import android.util.Log
import com.example.m_commerce.features.profile.data.local.CurrencyPreferencesDataSource
import com.example.m_commerce.features.profile.data.mapper.toLatestRatesResponse
import com.example.m_commerce.features.profile.data.mapper.toSymbolResponse
import com.example.m_commerce.features.profile.data.remote.CurrencyApiService
import com.example.m_commerce.features.profile.domain.entity.ApiConversionRateResponse
import com.example.m_commerce.features.profile.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class CurrencyRepositoryImpl(
    private val local: CurrencyPreferencesDataSource,
    private val remote: CurrencyApiService
) : CurrencyRepository {
    companion object {
        private const val ACCESS_KEY = "48212ef7d84112195dd36666"
    }

    private val _exchangeRateFlow = MutableStateFlow(local.getExchangeRate())
    override val exchangeRateFlow: StateFlow<Float> = _exchangeRateFlow.asStateFlow()

    override suspend fun fetchExchangeRate(symbol: String): Float = try {
        Log.d("TAG", "Calling API for: $symbol")

        val remoteResponse: ApiConversionRateResponse = remote.getLatestRates(
            apiKey = ACCESS_KEY,
            target = symbol
        )
      //  Log.d("TAG", "API success: ${remoteResponse.rates}")
        val exchangeRate = remoteResponse.toLatestRatesResponse()
        val rate = exchangeRate.rates[symbol]?.toFloat() ?: local.getExchangeRate()
        local.saveExchangeRate(rate)
        _exchangeRateFlow.value = rate
        rate
    } catch (e: Exception) {
        Log.e("TAG", "fetchExchangeRate failed", e)
        _exchangeRateFlow.value
    }


    override suspend fun getSupportedCurrencies(): Map<String, String> = try {
        val remoteResponse = remote.getSupportedSymbols(ACCESS_KEY)
        var resp = remoteResponse.toSymbolResponse()
        resp.symbols
    } catch (e: Exception) {
        Log.e("CurrencyRepo", "getSupportedCurrencies failed", e)
        emptyMap()
    }

    override fun getCachedExchangeRate(): Float {
        Log.i("TAG", "getCachedExchangeRate: ${_exchangeRateFlow.value} ")
        return _exchangeRateFlow.value
    }

    override fun getDefaultCurrencyCode(): String? = local.getDefaultCurrencyCode()

    override suspend fun saveDefaultCurrencyCode(code: String) {
        local.saveDefaultCurrencyCode(code)
    }
}
