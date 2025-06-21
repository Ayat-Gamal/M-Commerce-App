package com.example.m_commerce.features.profile.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m_commerce.features.profile.domain.usecase.GetCurrenciesUseCase
import com.example.m_commerce.features.profile.presentation.state.CurrencyState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase
) : ViewModel() {

    var state by mutableStateOf(CurrencyState())
        private set

    init {
        fetchCurrencies()
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
}
