package com.example.m_commerce.features.profile.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m_commerce.features.profile.domain.model.CurrencyDetails
import com.example.m_commerce.features.profile.domain.usecase.GetCurrenciesUseCase
import com.example.m_commerce.features.profile.domain.usecase.GetDefaultCurrencyUseCase
import com.example.m_commerce.features.profile.domain.usecase.SaveDefaultCurrencyUseCase
import com.example.m_commerce.features.profile.presentation.state.CurrencyState
import com.example.m_commerce.features.profile.presentation.state.DefaultCurrencyState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val saveUseCase: SaveDefaultCurrencyUseCase,
    private val getUseCase: GetDefaultCurrencyUseCase
) : ViewModel() {

    var state by mutableStateOf(CurrencyState())
        private set

    val defaultCurrencyState by mutableStateOf(DefaultCurrencyState(
        defaultCurrency = CurrencyDetails(
            currencyCode = "Loading",
            currencyName = "Loading",
            countryCode = "Loading",
            countryName = "Loading",
            status = "Loading",
            availableUntil = "Loading",
            icon = "Loading"
        ),
    ))

    init {
        fetchCurrencies()
        getDefaultCurrency()
    }

    private fun fetchCurrencies() {
        viewModelScope.launch {
            try {
                state = state.copy(isLoading = true)
                val currencies = getCurrenciesUseCase()
                state = state.copy(currencies = currencies, isLoading = false)
            } catch (e: Exception) {
                state = state.copy(error = e.localizedMessage ?: "Unknown error", isLoading = false)
            }
        }
    }

    fun saveDefaultCurrency(details: CurrencyDetails) {
        viewModelScope.launch {
            saveUseCase(details)
        }
    }

    fun getDefaultCurrency() {
        viewModelScope.launch {
            val result = getUseCase()
            Log.i("TAG", "getDefaultCurrency:$result")
            defaultCurrencyState.defaultCurrency = result
        }
    }
}
