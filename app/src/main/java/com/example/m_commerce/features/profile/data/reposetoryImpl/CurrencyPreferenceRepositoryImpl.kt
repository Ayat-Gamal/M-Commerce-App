package com.example.m_commerce.features.profile.data.reposetoryImpl

import android.util.Log
import com.example.m_commerce.features.profile.data.local.CurrencyPreferencesDataSource
import com.example.m_commerce.features.profile.data.remote.CurrencyApiService
import com.example.m_commerce.features.profile.domain.entity.LatestRatesResponse
import com.example.m_commerce.features.profile.domain.entity.SymbolResponse
import com.example.m_commerce.features.profile.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class CurrencyRepositoryImpl(
    private val local: CurrencyPreferencesDataSource,
    private val remote: CurrencyApiService
) : CurrencyRepository {
    companion object {
        private const val ACCESS_KEY = "49cb4c69eeeedd7de53a273b2e89dd85"
    }

    private val _exchangeRateFlow = MutableStateFlow(local.getExchangeRate())
    override val exchangeRateFlow: StateFlow<Float> = _exchangeRateFlow.asStateFlow()

    override suspend fun fetchExchangeRate(symbol: String): Float = try {
        Log.d("TAG", "Calling API for: $symbol")
        val resp: LatestRatesResponse = remote.getLatestRates(
            accessKey = ACCESS_KEY,
            symbols = symbol
        )
        Log.d("TAG", "API success: ${resp.rates}")

        val rate = resp.rates[symbol]?.toFloat() ?: local.getExchangeRate()
        local.saveExchangeRate(rate)
        _exchangeRateFlow.value = rate
        rate
    } catch (e: Exception) {
        Log.e("TAG", "fetchExchangeRate failed", e)
        _exchangeRateFlow.value
    }


    override suspend fun getSupportedCurrencies(): Map<String, String> = try {
        val resp: SymbolResponse = remote.getSupportedSymbols(ACCESS_KEY)
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


//import android.content.SharedPreferences
//import android.util.Log
//import com.example.m_commerce.features.profile.data.remote.CurrencyApiService
//import com.example.m_commerce.features.profile.domain.model.CurrencyDetails
//import com.example.m_commerce.features.profile.domain.repository.CurrencyRepository
//import com.google.gson.Gson
//import javax.inject.Inject
//
//class CurrencyPreferencesRepositoryImpl @Inject constructor(
//    private val sharedPreferences: SharedPreferences,
//    private val gson: Gson, private val apiService: CurrencyApiService
//) : CurrencyRepository {
//
//    companion object {
//        private const val KEY_CURRENCY_DETAILS = "currency_details"
//    }
//    override suspend fun saveCurrencyDetails(details: CurrencyDetails) {
//        val json = gson.toJson(details)
//        sharedPreferences.edit().putString(KEY_CURRENCY_DETAILS, json).apply()
//    }
//    override suspend fun getCurrencyDetails(): CurrencyDetails? {
//        val json = sharedPreferences.getString(KEY_CURRENCY_DETAILS, null) ?: return null
//        return gson.fromJson(json, CurrencyDetails::class.java)
//    }
//
//    override suspend fun getCurrencies(): List<CurrencyDetails> {
//        return try {
//            val response = apiService.getSupportedCurrencies()
//            response.supportedCurrenciesMap.mapNotNull { (_, dto) ->
//                try {
//                    val availableUntil = dto.availableUntil ?: return@mapNotNull null
//                    CurrencyDetails(
//                        currencyCode = dto.currencyCode ?: return@mapNotNull null,
//                        currencyName = dto.currencyName ?: "Unknown",
//                        countryCode = dto.countryCode ?: "N/A",
//                        countryName = dto.countryName ?: "N/A",
//                        status = dto.status ?: "Unknown",
//                        availableUntil = availableUntil,
//                        icon = dto.icon ?: ""
//                    )
//                } catch (e: Exception) {
//                    Log.e("CurrencyRepository", "Mapping error for dto: $dto", e)
//                    null
//                }
//            }
//        } catch (e: Exception) {
//            Log.e("CurrencyRepository", "Error fetching currencies", e)
//            emptyList()
//        }
//    }
//
//}
