package com.example.m_commerce.features.profile.presentation.state

import com.example.m_commerce.features.profile.domain.entity.SymbolResponse


data class CurrencyState(
    val currencies: List<SymbolResponse> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)
