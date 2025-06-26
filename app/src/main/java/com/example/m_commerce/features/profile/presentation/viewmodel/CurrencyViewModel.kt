package com.example.m_commerce.features.profile.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m_commerce.features.profile.domain.entity.SymbolResponse
import com.example.m_commerce.features.profile.domain.usecase.GetCurrenciesUseCase
import com.example.m_commerce.features.profile.domain.usecase.GetDefaultCurrencyUseCase
import com.example.m_commerce.features.profile.domain.usecase.GetExchangeRateUseCase
import com.example.m_commerce.features.profile.domain.usecase.SaveDefaultCurrencyUseCase
import com.example.m_commerce.features.profile.presentation.state.CurrencyState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val saveDefaultUseCase: SaveDefaultCurrencyUseCase,
    private val getDefaultUseCase: GetDefaultCurrencyUseCase,
    private val getExchangeRateUseCase: GetExchangeRateUseCase
) : ViewModel() {

    var state by mutableStateOf(CurrencyState())
        private set

    var defaultCurrencyCode by mutableStateOf<String?>(null)
        private set

    val exchangeRate = getExchangeRateUseCase.exchangeRateFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    var exchangeRateState by mutableStateOf<Float?>(null)
        private set

    init {
        loadInitialCurrencyData()
        observeExchangeRate()
    }
    private fun observeExchangeRate() {
        viewModelScope.launch {
            exchangeRate.collect { rate ->
                exchangeRateState = rate
            }
        }
    }


    private fun loadInitialCurrencyData() {
        viewModelScope.launch {
            val default = getDefaultUseCase()
            defaultCurrencyCode = default
            loadExchangeRate(default ?: "EGP")
            loadSupportedCurrencies()
        }
    }

    private fun loadSupportedCurrencies() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            try {
                val symbolsMap = getCurrenciesUseCase()
                val dto = SymbolResponse(
                    success = true,
                    symbols = symbolsMap
                )
                state = state.copy(currencies = listOf(dto), isLoading = false)
            } catch (e: Exception) {
                Log.i("TAG", "loadSupportedCurrencies:${e} ")
                state = state.copy(error = e.localizedMessage ?: "Unknown error", isLoading = false)
            }
        }
    }

    fun saveDefaultCurrency(code: String) {
        viewModelScope.launch {
            try {
                saveDefaultUseCase(code)
                defaultCurrencyCode = code
                loadExchangeRate(code)
            } catch (e: Exception) {
                Log.e("TAG", "Failed to save default", e)
            }
        }
    }

    fun loadExchangeRate(symbol: String) {
        viewModelScope.launch {
            try {
                Log.i("TAG", "loadExchangeRate: $symbol")
                getExchangeRateUseCase(symbol)
            } catch (e: Exception) {
                Log.e("TAG", "Failed to fetch rate", e)
            }
        }
    }

    fun formatPrice(originalPrice: String?): String {
        val rate = exchangeRateState ?: return "Loading..."
        val currency = defaultCurrencyCode ?: "EGP"

        return if (rate != null) {
            val value = originalPrice?.toFloatOrNull() ?: 0f
            val converted = value * rate
            "%.2f %s".format(converted, currency)
        } else {
            "Loading..."
        }
    }
}
